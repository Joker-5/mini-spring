package com.john.doe.mini.test.web.service;

import com.john.doe.mini.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    private BaseBaseService bbs;

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public BaseService() {
    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        bbs.sayHello();
    }

    public String getHello() {
        return "Base Service get Hello.";
    }
}
