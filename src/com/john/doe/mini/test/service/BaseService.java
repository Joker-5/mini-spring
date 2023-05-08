package com.john.doe.mini.test.service;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public class BaseService {
    private BaseBaseService bbs;

    public BaseService() {
        
    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        bbs.sayHello();
    }

    public BaseService(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }
}
