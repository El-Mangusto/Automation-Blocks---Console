package org.example.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ActionBlock {
    private final String name;
    protected Map<String, String> configData = new HashMap<>();

    public ActionBlock(String name) {
        this.name = name;
    }

    public abstract void execute();

    public abstract void configure(Map<String, String> params);

    public abstract List<String> getConfigKeys();

    public abstract void log();

    public abstract ParamKind getParamKind(String key);

    public String getName() {
        return name;
    }

    public Map<String, String> getConfigData() {
        return new HashMap<>(configData);
    }
}