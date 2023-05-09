package com.john.doe.mini.test.service;

import com.john.doe.mini.beans.factory.annotation.Autowired;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public class CService {
    @Autowired
    private DService dservice;

    public void sayHello() {
        System.out.println("CService invoke DService~");
        dservice.sayHello();
    }

    public void setDservice(DService dservice) {
        this.dservice = dservice;
    }
}
