package org.example.variables;

import java.util.List;

public class Variable {
    private final String name;
    private final VariableType type;
    private Object value;

    public Variable(String name, VariableType type) {
        this.name = name;
        this.type = type;
        this.value = null;
    }

    public Variable(String name, VariableType type,  Object value) {
        this.name = name;
        this.type = type;
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public VariableType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if(!isCompatible(type, value)) {
            throw new IllegalArgumentException("Value does not match type " + type);
        }
        this.value = value;
    }

    public boolean isCompatible(VariableType type, Object value) {
        return switch (type) {
            case NUMBER -> (value instanceof Number);
            case STRING -> (value instanceof String);
            case BOOLEAN -> (value instanceof Boolean);
            case LIST_NUMBER -> (value instanceof List<?> list &&
                    list.stream().allMatch(x -> x instanceof Number));
            case LIST_STRING -> (value instanceof List<?> list &&
                    list.stream().allMatch(x -> x instanceof String));

        };
    }
}
