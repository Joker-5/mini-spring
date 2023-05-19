package com.john.doe.mini.beans.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        propertyValueList = new ArrayList<>(16);
    }

    public PropertyValues(Map<String, Object> map) {
        propertyValueList = new ArrayList<>(16);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            propertyValueList.add(new PropertyValue(entry.getKey(), entry.getValue()));
        }
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public int size() {
        return propertyValueList.size();
    }

    public void addPropertyValue(PropertyValue pv) {
        propertyValueList.add(pv);
    }

    // TODO
//    public void addPropertyValue(String propertyName, Object propertyValue) {
//        addPropertyValue(new PropertyValue(propertyName, propertyValue));
//    }

    public void removePropertyValue(PropertyValue pv) {
        propertyValueList.remove(pv);
    }

    public void removePropertyValue(String propertyName) {
        propertyValueList.remove(getPropertyValue(propertyName));
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public Object get(String propertyName) {
        PropertyValue pv = getPropertyValue(propertyName);
        return pv != null ? pv.getValue() : null;
    }

    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    public boolean isEmpty() {
        return propertyValueList.isEmpty();
    }

}
