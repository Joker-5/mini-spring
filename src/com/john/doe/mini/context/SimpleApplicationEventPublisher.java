package com.john.doe.mini.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/10.
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {
    private List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        listeners.add(listener);
    }
}
