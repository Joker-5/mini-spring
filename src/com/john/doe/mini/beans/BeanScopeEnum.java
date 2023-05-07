package com.john.doe.mini.beans;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public enum BeanScopeEnum {
    SINGLETON("singleton"),
    PROTOTYPE("prototype");

    private String name;

    BeanScopeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
