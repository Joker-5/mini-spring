package com.john.doe.mini.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public class PropertyEditorRegistrySupport {
    private Map<Class<?>, PropertyEditor> defaultEditors;

    private Map<Class<?>, PropertyEditor> customEditors;

    protected void registerDefaultEditors() {
        createDefaultEditors();
    }

    private void createDefaultEditors() {
        defaultEditors = new HashMap<>(64);

        defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
        defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
        defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
        defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        defaultEditors.put(String.class, new StringEditor(String.class, true));
    }

    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        return defaultEditors.get(requiredType);
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        if (customEditors == null) {
            customEditors = new HashMap<>(16);
        }

        customEditors.put(requiredType, propertyEditor);
    }

    public PropertyEditor findCustomPropertyEditor(Class<?> requiredType) {
        return getCustomEditor(requiredType);
    }

    public boolean hasCustomEditorForElement(Class<?> elementType) {
        return elementType != null && customEditors != null && customEditors.containsKey(elementType);
    }

    public PropertyEditor getCustomEditor(Class<?> requiredType) {
        if (requiredType == null || customEditors == null) {
            return null;
        }

        return customEditors.get(requiredType);
    }
}
