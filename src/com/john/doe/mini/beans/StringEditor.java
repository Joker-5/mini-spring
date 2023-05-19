package com.john.doe.mini.beans;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public class StringEditor implements PropertyEditor {
    private Class<String> stringClass;

    private String stringFormat;

    private boolean allowEmpty;

    private Object value;

    public StringEditor(Class<String> stringClass, boolean allowEmpty) {
        this(stringClass, "", allowEmpty);
    }

    public StringEditor(Class<String> stringClass, String stringFormat, boolean allowEmpty) {
        this.stringClass = stringClass;
        this.stringFormat = stringFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getAsText() {
        return value.toString();
    }
}
