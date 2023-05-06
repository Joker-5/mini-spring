package com.john.doe.test;

import com.john.doe.mini.context.ClasspathXmlApplicationContext;
import com.john.doe.test.service.AService;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class TestMain {
    public static void main(String[] args) {
        ClasspathXmlApplicationContext ctx = new ClasspathXmlApplicationContext("applicationContext.xml");

        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }
}
