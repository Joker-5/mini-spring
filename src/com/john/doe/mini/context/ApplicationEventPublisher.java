package com.john.doe.mini.context;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public interface ApplicationEventPublisher {
    /**
     * publish container event
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
