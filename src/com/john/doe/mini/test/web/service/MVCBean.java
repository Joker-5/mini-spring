package com.john.doe.mini.test.web.service;

import com.john.doe.mini.web.RequestMapping;

/**
 * Created by JOHN_DOE on 2023/5/13.
 */
public class MVCBean {
    @RequestMapping("/test1")
    public String doTest1() {
        return "test 1, hello world!";
    }

    @RequestMapping("/test2")
    public String doTest2() {
        return "test 2, hello world!";
    }
}
