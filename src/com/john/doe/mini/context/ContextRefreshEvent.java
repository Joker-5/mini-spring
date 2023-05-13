package com.john.doe.mini.context;

/**
 * Created by JOHN_DOE on 2023/5/10.
 */
public class ContextRefreshEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object args0) {
        super(args0);
    }

    public String toString() {
        return this.msg;
    }
}
