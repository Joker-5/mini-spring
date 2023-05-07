package com.john.doe.mini.context;

import java.util.EventObject;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public class ApplicationEvent extends EventObject {
    public static final long serialVersionUID = 1L;

    public ApplicationEvent(Object args0) {
        super(args0);
    }
}
