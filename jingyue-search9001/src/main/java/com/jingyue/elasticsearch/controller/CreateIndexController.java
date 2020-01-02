package com.jingyue.elasticsearch.controller;

import com.jingyue.common.entity.Result;
import com.jingyue.common.entity.StatusCode;
import com.jingyue.elasticsearch.service.CreateIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateIndexController {
    @Autowired
    private CreateIndexService service;

    /**
     * 创建 索引 及类型
     *
     * @return
     */
    @RequestMapping(value = "es/create")
    public Result createIndex() {
        boolean flag = service.createIndex();
        if (flag) {
            return new Result(true, StatusCode.OK.getCode(), "创建成功", null);
        }
        return new Result(false, StatusCode.ERROR.getCode(), "创建失败", null);
    }

    /**
     * 删除索引
     *
     * @return
     */
    @RequestMapping(value = "es/delete")
    public Result deleteIndex() {
        boolean flag = service.delIndex();
        if (flag) {
            return new Result(true, StatusCode.OK.getCode(), "删除成功", null);
        }
        return new Result(false, StatusCode.ERROR.getCode(), "删除失败", null);
    }

    /**
     * 添加数据
     *
     * @return
     */
    @RequestMapping(value = "es/insert")
    public Result insertDb() {
        boolean flag = service.insertDb();
        if (flag) {
            return new Result(true, StatusCode.OK.getCode(), "添加成功", null);
        }
        return new Result(false, StatusCode.ERROR.getCode(), "添加失败", null);
    }

    /**
     * 将数据库的数据导入到es中
     *
     * @return
     */
    @RequestMapping(value = "es/import")
    public Result importDb() {
        boolean flag = service.importDb();
        if (flag) {
            return new Result(true, StatusCode.OK.getCode(), "导入成功", null);
        }
        return new Result(false, StatusCode.ERROR.getCode(), "导入失败", null);
    }

    /**
     * 将数据库的数据导入到es中
     *
     * @return
     */
    @RequestMapping(value = "/es/select")
    public Result selectItems() {

        return new Result(true, StatusCode.OK.getCode(), "查询成功", service.selectItems());
    }
}
