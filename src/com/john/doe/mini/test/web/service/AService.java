package com.john.doe.mini.test.web.service;

public class AService {
    private String name;
    private int level;
    private String property1;
    private String property2;
    private BaseService ref1;

    public BaseService getRef1() {
        return ref1;
    }

    public void setRef1(BaseService bs) {
        this.ref1 = bs;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public AService() {
    }

    public AService(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public void sayHello() {
        System.out.print(this.property1 + "," + this.property2);
        ref1.sayHello();
    }
}
