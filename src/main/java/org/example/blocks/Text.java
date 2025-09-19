package org.example.blocks;

import org.example.variables.Variable;
import org.example.workflow.ActionBlock;

import java.util.List;
import java.util.Map;

public class Text extends ActionBlock {
    private String text;

    public Text() {
        super("Text");
    }

    @Override
    public void execute(Variable var) {
        var.setValue(text);
        log();
    }

    @Override
    public void configure(Map<String, String> params) {
        this.text = params.get("text");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("text");
    }

    @Override
    public void log() {
        System.out.println(getName() + "block: " + text);
    }

}
