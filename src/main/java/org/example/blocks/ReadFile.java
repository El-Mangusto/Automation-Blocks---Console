package org.example.blocks;

import org.example.variables.Variable;
import org.example.workflow.ActionBlock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReadFile extends ActionBlock {
    private String pathFile;

    public ReadFile() {
        super("Read File");
    }

    @Override
    public void execute(Variable var) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("ReadFile: Failed to read to file '" + pathFile + "'", e);
        }
        log();
        var.setValue(content.toString());
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
        System.out.println("ReadFileBlock: " + pathFile + " read");
    }
}
