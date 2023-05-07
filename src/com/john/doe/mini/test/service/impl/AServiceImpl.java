package com.john.doe.mini.test.service.impl;

import com.john.doe.mini.test.service.AService;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class AServiceImpl implements AService {
    private String p1;
    private String p2;
    private String name;
    private Integer level;

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    @Override
    public void sayHello() {
        System.out.println(name + "," + level + "," + p1 + "," + p2);
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
