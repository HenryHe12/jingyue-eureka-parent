/**
 * 文件名：SoapApiInvokeUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：方法调用工具
 */
package com.jingyue.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 方法调用工具
 *
 * @version 1.0
 * @author 张晓龙.
 * @date 2017/11/8.
 */
public final class SoapApiInvokeUtil {
    private SoapApiInvokeUtil() {

    }

    private static Logger logger = LoggerFactory.getLogger(SoapApiInvokeUtil.class);
    private static final int NUM = 1024;

    /**
     * SOAP调用方法类
     *
     * @param wsurl          url地址
     * @param reqHeaderUrl   url地址
     * @param requestBodyXml 请求体报文
     * @return String 字符串
     * @throws Exception 异常类
     */
    public static String invoke(String wsurl, String reqHeaderUrl, String requestBodyXml) throws Exception {
        String s = "";
        //服务的地址
        URL wsUrl = new URL(wsurl);

        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

        OutputStream os = conn.getOutputStream();

        String soapXml = RequestBodyUtil.getRequestBody(reqHeaderUrl, requestBodyXml);
        os.write(soapXml.getBytes());
        os.close();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();

            byte[] b = new byte[NUM];
            int len = 0;

            while ((len = is.read(b)) != -BigDecimal.ONE.intValue()) {
                String ss = new String(b, 0, len, "UTF-8");
                s += ss;
            }
            is.close();
        } else {
            InputStream is = conn.getErrorStream();
            String error = "";
            byte[] b = new byte[NUM];
            int len = 0;

            while ((len = is.read(b)) != -BigDecimal.ONE.intValue()) {
                String ss = new String(b, 0, len, "UTF-8");
                error += ss;
            }
            logger.error(error.substring(error.indexOf(
                    "<faultstring>") + "<faultstring>".length(), error.indexOf("</faultstring>")));
            is.close();

        }
        conn.disconnect();

        return s;
    }
}
