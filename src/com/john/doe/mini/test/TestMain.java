package com.john.doe.mini.test;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.context.ClasspathXmlApplicationContext;
import com.john.doe.mini.test.service.AService;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class TestMain {
    public static void main(String[] args) throws BeansException {
        ClasspathXmlApplicationContext ctx = new ClasspathXmlApplicationContext("applicationContext.xml");

        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }
}
