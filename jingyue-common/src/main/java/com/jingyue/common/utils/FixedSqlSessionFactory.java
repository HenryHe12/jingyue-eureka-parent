/**
 * 文件名：FixedSqlSessionFactory.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：修改的SqlSessionFactoryBean
 */
package com.jingyue.common.utils;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 修改的SqlSessionFactory
 *
 * @version 1.0
 * @author shenqb
 * @date 2017/10/19.
 */
public class FixedSqlSessionFactory extends SqlSessionFactoryBean {

    private final Logger logger = LoggerFactory.getLogger(FixedSqlSessionFactory.class);

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        try {
            return super.buildSqlSessionFactory();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("============================= error message : {} ======================================",
                        e.getMessage());
                logger.error("============================= error cause : {} ======================================",
                        e.getCause().toString());
                logger.error("This is the error stack trace : ", e);
            }
        } finally {
            ErrorContext.instance().reset();
        }
        return null;
    }
}