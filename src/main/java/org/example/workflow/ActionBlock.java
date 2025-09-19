package org.example.workflow;

import org.example.variables.Variable;

import java.util.List;
import java.util.Map;

public abstract class ActionBlock {
    private final String name;

    public ActionBlock(String name) {
        this.name = name;
    }

    public abstract void execute(Variable var);

    public abstract void configure(Map<String, String> params);

    public abstract List<String> getConfigKeys();

    public abstract void log();

    public String getName() {
        return name;
    }
}