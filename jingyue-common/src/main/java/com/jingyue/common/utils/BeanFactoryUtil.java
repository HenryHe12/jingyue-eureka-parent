/**
 * 文件名：BeanFactoryUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：bean操作工具类
 */
package com.jingyue.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * bean工厂工具类
 *
 * @author allen
 * @version 1.0
 * @date 2018/2/26 22:29
 */
@Component
public class BeanFactoryUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    /**
     * 设置工厂bean
     *
     * @param beanFactory param
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanFactoryUtil.beanFactory = beanFactory;
    }

    /**
     * 获取Bean
     *
     * @param beanName param
     * @param <T>      泛型
     * @return T 泛型
     */
    public static <T> T getBean(String beanName) {
        if (BeanFactoryUtil.beanFactory != null) {
            return (T) BeanFactoryUtil.beanFactory.getBean(beanName);
        }
        return null;
    }
}
