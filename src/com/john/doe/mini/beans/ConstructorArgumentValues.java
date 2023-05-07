package com.john.doe.mini.beans;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by JOHN_DOE on 2023/5/7.
 */
public class ConstructorArgumentValues {
    private final Map<Integer, ConstructorArgumentValue> indexedConstructorArgumentValues = new HashMap<>(0);

    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new LinkedList<>();

    public boolean hasIndexedConstructorArgumentValue(int index) {
        return indexedConstructorArgumentValues.containsKey(index);
    }

    public ConstructorArgumentValue getIndexedConstructorArgumentValue(int index) {
        return indexedConstructorArgumentValues.get(index);
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
        if (Objects.nonNull(newValue.getName())) {
            genericConstructorArgumentValues.removeIf(currentValue -> newValue.getName().equals(currentValue.getName()));
            genericConstructorArgumentValues.add(newValue);
        }
    }

    private void addConstructorArgumentValue(Integer key, ConstructorArgumentValue newValue) {
        indexedConstructorArgumentValues.put(key, newValue);
    }


}
