package com.john.doe.mini.beans;


import com.john.doe.mini.util.NumberUtils;
import com.john.doe.mini.util.StringUtils;

import java.text.NumberFormat;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public class CustomNumberEditor implements PropertyEditor {
    // type of number
    private Class<? extends Number> numberClass;

    private NumberFormat numberFormat;

    private boolean allowEmpty;

    private Object value;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        if (allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else if (numberFormat != null) {
            setValue(NumberUtils.parseNumber(text, numberClass, numberFormat));
        } else {
            setValue(NumberUtils.parseNumber(text, numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            this.value = NumberUtils.convertNumberToTargetClass((Number) value, numberClass);
        } else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getAsText() {
        if (value == null) {
            return "";
        }
        if (numberFormat != null) {
            return numberFormat.format(value);
        } else {
            return value.toString();
        }
    }
}
