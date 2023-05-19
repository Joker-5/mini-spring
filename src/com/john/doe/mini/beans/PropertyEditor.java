package com.john.doe.mini.beans;

/**
 * Created by JOHN_DOE on 2023/5/18.
 */
public interface PropertyEditor {
    void setAsText(String text);
    
    void setValue(Object value);
    
    Object getValue();
    
    Object getAsText();
}
