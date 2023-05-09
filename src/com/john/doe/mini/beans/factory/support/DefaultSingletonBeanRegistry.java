package com.john.doe.mini.beans.factory.support;

import com.john.doe.mini.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected List<String> beanNames = new ArrayList<>();

    protected ConcurrentHashMap<String, Object> singletons = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (singletons) {
            this.beanNames.add(beanName);
            this.singletons.put(beanName, singletonObject);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    @Override
    public void removeSingleton(String beanName) {
        synchronized (singletons) {
            beanNames.remove(beanName);
            singletons.remove(beanName);
        }
    }
}
