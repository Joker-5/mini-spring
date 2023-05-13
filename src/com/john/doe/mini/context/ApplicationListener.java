package com.john.doe.mini.context;

import java.util.EventListener;

/**
 * Created by JOHN_DOE on 2023/5/10.
 */
public class ApplicationListener implements EventListener {
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
