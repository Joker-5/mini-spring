package com.john.doe.mini.test.web.service;

public class BaseBaseService {
    private AService as;

    public AService getAs() {
        return as;
    }

    public void setAs(AService as) {
        this.as = as;
    }

    public BaseBaseService() {
    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");

    }
}
