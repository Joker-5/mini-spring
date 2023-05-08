package com.john.doe.mini.beans;

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

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    // by name
//    public PropertyValue(String name, Object value) {
//        this.name = name;
//        this.value = value;
//    }
//
//    public PropertyValue(String type, String name, Object value) {
//        this.type = type;
//        this.name = name;
//        this.value = value;
//    }

    public String getType() {
        return type;
    }

    //
//    public void setType(String type) {
//        this.type = type;
//    }
//
    public String getName() {
        return name;
    }

    //
//    public void setName(String name) {
//        this.name = name;
//    }
//
    public Object getValue() {
        return value;
    }
//
//    public void setValue(Object value) {
//        this.value = value;
//    }

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
