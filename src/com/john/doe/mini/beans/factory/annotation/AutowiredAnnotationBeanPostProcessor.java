package com.john.doe.mini.beans.factory.annotation;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.BeanFactory;
import com.john.doe.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.john.doe.mini.beans.factory.config.BeanPostProcessor;
import com.john.doe.mini.util.ArrayUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * Created by JOHN_DOE on 2023/5/8.
 */
@Slf4j
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        if (!ArrayUtils.isEmpty(fields)) {
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    String fieldName = field.getName();
                    log.info("autowired annotation get field name: {}", fieldName);
                    // TODO now just get by fieldName, if fieldName mismatch beanName, there' ll make some mistakes, 
                    //  so next should autowired by type
                    Object autowiredObj = getBeanFactory().getBean(fieldName);
                    field.setAccessible(true);
                    try {
                        field.set(bean, autowiredObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    log.info("autowired [{}] for bean [{}]", field, bean);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
