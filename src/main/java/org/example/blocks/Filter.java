package org.example.blocks;

import org.example.variables.Variable;
import org.example.workflow.ActionBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Filter extends ActionBlock {
    private String filter;

    public Filter() {
        super("Filter");
    }

    @Override
    public void execute(Variable var) {
        if (var == null) return;

        Object value = var.getValue();
        if (!(value instanceof List<?>)) {
            return;
        }

        List<?> list = (List<?>) value;
        List<Object> filtered = new ArrayList<>();

        for (Object item : list) {
            if (item == null) continue;

            String[] words = item.toString().split("\\s+");
            for (String w : words) {
                if (w.equals(filter)) {
                    filtered.add(item);
                    break;
                }
            }
        }
        log();
        var.setValue(filtered);
    }

    @Override
    public void configure(Map<String, String> params) {
        this.filter = params.get("filter");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("filter");
    }

    @Override
    public void log() {
        System.out.println(getName() + " block: " + filter + " complete");
    }
}