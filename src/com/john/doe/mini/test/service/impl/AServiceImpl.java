package com.john.doe.mini.test.service.impl;

import com.john.doe.mini.test.service.AService;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class AServiceImpl implements AService {
    private String p1;
    @Override
    public void sayHello() {
        System.out.println("hello~~ " + p1);
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }
}
