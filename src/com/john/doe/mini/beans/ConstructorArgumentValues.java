package com.john.doe.mini.beans;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new ArrayList<>();

    public ConstructorArgumentValue getIndexedConstructorArgumentValue(int index) {
        if (index < 0 || index >= genericConstructorArgumentValues.size()) {
            return null;
        }
        return genericConstructorArgumentValues.get(index);
    }

    public void addConstructorArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        genericConstructorArgumentValues.add(constructorArgumentValue);
    }

    public void addGenericArgumentValue(String type, Object value) {
        genericConstructorArgumentValues.add(new ConstructorArgumentValue(type, value));
    }

    public ConstructorArgumentValue getGenericConstructorArgumentValue(String requiredName) {
        for (ConstructorArgumentValue valueHolder : genericConstructorArgumentValues) {
            if (valueHolder.getValue() != null && (!valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getConstructorArgumentCount() {
        return genericConstructorArgumentValues.size();
    }

    public boolean isEmpty() {
        return genericConstructorArgumentValues.isEmpty();
    }

    private void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            genericConstructorArgumentValues.removeIf(currentValue -> newValue.getName().equals(currentValue.getName()));
            genericConstructorArgumentValues.add(newValue);
        }
    }


}
