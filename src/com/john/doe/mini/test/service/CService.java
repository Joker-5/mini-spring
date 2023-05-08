package com.john.doe.mini.test.service;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public class CService {
    private DService ds;

    public void sayHello() {
        System.out.println("CService invoke DService~");
        ds.sayHello();
    }

    public void setDs(DService ds) {
        this.ds = ds;
    }
}
