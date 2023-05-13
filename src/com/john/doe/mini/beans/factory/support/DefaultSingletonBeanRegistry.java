package com.john.doe.mini.beans.factory.support;

import com.john.doe.mini.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JOHN_DOE on 2023/5/6.
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected List<String> beanNames = new ArrayList<>();

    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    protected Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

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

    public void registerDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeans = dependentBeanMap.get(beanName);
        if (dependentBeans != null && dependentBeans.contains(beanName)) {
            return;
        }

        synchronized (dependentBeanMap) {
            dependentBeans = dependentBeanMap.computeIfAbsent(beanName, k -> new HashSet<>());
            dependentBeans.add(dependentBeanName);
        }
        synchronized (dependenciesForBeanMap) {
            Set<String> dependenciesForBean = dependenciesForBeanMap.computeIfAbsent(dependentBeanName, k -> new HashSet<>());
            dependenciesForBean.add(beanName);
        }
    }

    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = dependentBeanMap.get(beanName);

        if (dependentBeans == null) {
            return new String[0];
        }
        return dependentBeans.toArray(new String[0]);
    }

    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBean = dependenciesForBeanMap.get(beanName);

        if (dependenciesForBean == null) {
            return new String[0];
        }
        return dependenciesForBean.toArray(new String[0]);

    }
}
