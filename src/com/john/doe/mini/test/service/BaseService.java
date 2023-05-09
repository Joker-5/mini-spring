package com.john.doe.mini.test.service;

import com.john.doe.mini.beans.factory.annotation.Autowired;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public class BaseService {
    @Autowired
    private BaseBaseService basebaseservice;

    public BaseService() {

    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        basebaseservice.sayHello();
    }

    public void setBasebaseservice(BaseBaseService basebaseservice) {
        this.basebaseservice = basebaseservice;
    }

    public BaseService(BaseBaseService basebaseservice) {
        this.basebaseservice = basebaseservice;
    }
}
