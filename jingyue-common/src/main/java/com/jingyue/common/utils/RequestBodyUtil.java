/**
 * 文件名：RequestBodyUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：请求体拼接工具类
 */
package com.jingyue.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * 请求体拼接工具类
 *
 * @author 张晓龙.
 * @version 1.0
 * @date 2017/11/8.
 */
public final class RequestBodyUtil {
    private static Logger logger = LoggerFactory.getLogger(RequestBodyUtil.class);

    private RequestBodyUtil() {
    }

    /**
     * 获取请求体
     *
     * @param url            url
     * @param requestBodyXml 请求报文
     * @return String
     */
    public static String getRequestBody(String url, String requestBodyXml) {
        StringBuilder soap = new StringBuilder(); //构造请求报文
        soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n");
        soap.append(url);
        soap.append(">\n");
        soap.append("<soapenv:Header/>\n");
        soap.append("<soapenv:Body>\n");

        soap.append(requestBodyXml);

        soap.append("</soapenv:Body>\n");
        soap.append("</soapenv:Envelope>\n");
        String requestSoap = soap.toString();

        return requestSoap;
    }

    /**
     * 对象转Xml方法
     *
     * @param object 对象
     * @return 返回xmlStr
     */
    public static String object2Xml(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();

            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); // 编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, true); // 是否省略xml头信息
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);

            return new String(writer.getBuffer());

        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }
}
