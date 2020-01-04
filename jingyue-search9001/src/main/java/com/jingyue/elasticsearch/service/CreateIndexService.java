package com.jingyue.elasticsearch.service;

import com.jingyue.api.service.DeptFeiginClientService;
import com.jingyue.common.entity.Result;
import com.jingyue.elasticsearch.bean.Item;
import org.apache.tomcat.util.json.JSONParser;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CreateIndexService {
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private TransportClient client;
    @Autowired
    private DeptFeiginClientService clientService;


    public static final String index = "item";

    public static final String type = "docs";

    /**
     * 创建索引
     *
     * @return
     */
    public boolean createIndex() {
        return template.createIndex(Item.class);
    }

    /**
     * 删除索引
     *
     * @return
     */
    public boolean delIndex() {

        return template.deleteIndex(Item.class);
    }

    /**
     * 添加数据
     *
     * @return
     */
    public boolean insertDb() {
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("id", 3)
                    .field("title", "央行降准0.5个百分点 释放长期资金约8000多亿元")
                    .field("category", "银行")
                    .field("FieldType", "腾讯")
                    .field("price", "123.77")
                    .field("images", "https://new.qq.com/omn/FIN20200/FIN2020010103173900.html").endObject();
            IndexResponse response = client.prepareIndex(index, type)
                    .setSource(content)
                    .get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 导入数据,通过feign调用远程部门微服务，将部门数据导入到es中
     *
     * @return
     */
    public boolean importDb() {
        try {
            Result deptAll = clientService.getDeptAll();
            if (deptAll != null) {
                List<LinkedHashMap<String, Object>> deptList = (List<LinkedHashMap<String, Object>>) deptAll.getData();
                if (null != deptList && deptList.size() > 0) {
                    for (int i = 0; i < deptList.size(); i++) {
                        LinkedHashMap<String, Object> dept = (LinkedHashMap<String, Object>) deptList.get(i);
                        XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                                .field("id", dept.get("id"))
                                .field("title", dept.get("deptAddress"))
                                .field("category", dept.get("deptName"))
                                .field("FieldType", dept.get("deptNo"))
                                .field("price", "123.77")
                                .field("images", "https://new.qq.com/omn/FIN20200/" + dept.get("id") + ".html").endObject();
                        client.prepareIndex(index, type)
                                .setSource(content)
                                .get();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询es中的数据
     *
     * @return
     */
    public List<Object> selectItems() {
        List<Object> ls = new ArrayList<>();
        try {
            QueryBuilder qb = new MatchAllQueryBuilder();
            SearchResponse response = client.prepareSearch("item").setTypes("docs").setQuery(qb).setFrom(0)
                    .setSize(100).get();

            SearchHits searchHits = response.getHits();
            for (SearchHit hit : searchHits.getHits()) {
                JSONParser parser = new JSONParser(hit.getSourceAsString());
                LinkedHashMap<String, Object> objectMap = parser.parseObject();
                System.out.println(objectMap);
                ls.add(objectMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ls;
    }
}
