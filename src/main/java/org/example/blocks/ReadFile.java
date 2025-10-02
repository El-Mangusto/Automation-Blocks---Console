package org.example.blocks;

import org.example.variables.ExecutionContext;
import org.example.variables.VariableType;
import org.example.workflow.ActionBlock;
import org.example.workflow.ParamKind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReadFile extends ActionBlock {
    private String pathFile;
    private String outputVarName;

    public ReadFile() {
        super("Read File");
    }

    @Override
    public void execute() {

        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("ReadFile: Failed to read to file '" + pathFile + "'", e);
        }

        ExecutionContext.setVariable(outputVarName, VariableType.STRING, content.toString());
        log();
    }

    @Override
    public void configure(Map<String, String> params) {
        this.configData.putAll(params);
        this.pathFile = params.get("pathFile");
        this.outputVarName = params.get("outputVarName");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("pathFile", "outputVarName");
    }

    @Override
    public void log() {
        System.out.println("ReadFileBlock: " + pathFile + " read");
    }

    @Override
    public ParamKind getParamKind(String key) {
        return switch (key) {
            case "pathFile" -> ParamKind.STRING;
            case "outputVarName" -> ParamKind.VARIABLE;
            default -> throw new IllegalArgumentException("Unknown config key: " + key);
        };
    }
}
