package com.john.doe.mini.test;

import com.john.doe.mini.test.service.A;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
public class TestMain2 {
    public static void main(String[] args) {
        A aClass = new A();
        int aNum = 10;
//        aClass.pause(aNum);
        List<Integer> list = new ArrayList<>();
        list.add(aNum);
        System.out.println(list.size() + " before");
        aClass.clearList(list);

        System.out.println(list.size());
    }
}
