package org.example.blocks;

import org.example.variables.Variable;
import org.example.workflow.ActionBlock;

import java.util.List;
import java.util.Map;

public class SplitString extends ActionBlock {
    private String delimiter;

    public SplitString() {
        super("SplitString");
    }

    @Override
    public void execute(Variable var) {
        if (var == null) {
            throw new IllegalArgumentException("SplitStringBlock: Input is null");
        }
        String str = (String) var.getValue();
        var.setValue(List.of(str.split(delimiter)));
        log();
    }

    @Override
    public void configure(Map<String, String> params) {
        this.delimiter = params.get("delimiter");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("delimiter");
    }

    @Override
    public void log() {
        System.out.println(getName() + " block: split by '" + delimiter + "'");
    }
}