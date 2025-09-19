package org.example.blocks;

import org.example.variables.Variable;
import org.example.workflow.ActionBlock;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WriteFile extends ActionBlock {
    private String pathFile;

    public WriteFile() {
        super("Write File");
    }

    @Override
    public void execute(Variable var) {
        if (var == null) return;

        try (FileWriter fileWriter = new FileWriter(pathFile)) {
            Object value = var.getValue();
            if (value instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<String> inputList = (List<String>) value;
                String joined = String.join("\n", inputList);
                fileWriter.write(joined);
            } else {
                fileWriter.write(value.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("WriteFile: Failed to write to file '" + pathFile + "'", e);
        }
        log();
    }

    @Override
    public void configure(Map<String, String> params) {
        this.pathFile = params.get("pathFile");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("pathFile");
    }

    @Override
    public void log() {
        System.out.println(getName() + " block: " + pathFile + " saved");
    }
}