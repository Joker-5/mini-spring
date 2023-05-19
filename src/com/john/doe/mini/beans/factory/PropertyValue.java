package com.john.doe.mini.beans.factory;

/**
 * Created by JOHN_DOE on 2023/5/7.
 * <p>
 * Setter Dependency Injection Args
 */
public class PropertyValue {
    private final String type;
    private final String name;
    private final Object value;
    // property is reffed or not 
    private final boolean isRef;

    public PropertyValue(String name, Object value) {
        this("", name, value, false);
    }

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean getIsRef() {
        return isRef;
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", isRef=" + isRef +
                '}';
    }
}
