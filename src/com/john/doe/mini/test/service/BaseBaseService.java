package com.john.doe.mini.test.service;

import com.john.doe.mini.test.service.impl.AServiceImpl;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public class BaseBaseService {
    private AServiceImpl as;

    public BaseBaseService() {
        
    }

    public BaseBaseService(AServiceImpl as) {
        this.as = as;
    }

    public AServiceImpl getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");
        as.sayHello();
    }
}
