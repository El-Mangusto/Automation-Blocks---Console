package org.example.variables;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {
    private static final Map<String, Variable> variables = new HashMap<>();

    public static void setVariable(String name, VariableType type, Object value) {
        if (variables.containsKey(name)) {
            Variable var = variables.get(name);
            if (var.getType() != type) {
                throw new IllegalArgumentException(
                        "Variable '" + name + "' already exists with different type"
                );
            }
            var.setValue(value);
        } else {
            variables.put(name, new Variable(name, type, value));
        }
    }

    public static Variable getVariable(String name) {
        Variable var = variables.get(name);
        if (var == null) {
            throw new IllegalArgumentException("Variable '" + name + "' does not exist");
        }
        return var;
    }

    public static Object getValue(String name) {
        return getVariable(name).getValue();
    }

    public static void remove(String name) {
        variables.remove(name);
    }

    public static void clear() {
        variables.clear();
    }

    public static boolean containsVariable(String variableName) {
        return variables.containsKey(variableName);
    }

    public static void showVariables() {
        if (variables.isEmpty()) {
            System.out.println("No variables");
        }
        for (Map.Entry<String, Variable> entry : variables.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getValue());
        }
    }

    public static boolean exists(String name) {
        return variables.containsKey(name);
    }

}
