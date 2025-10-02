package org.example.blocks;

import org.example.variables.ExecutionContext;
import org.example.variables.Variable;
import org.example.variables.VariableType;
import org.example.workflow.ActionBlock;
import org.example.workflow.ParamKind;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WriteFile extends ActionBlock {
    private String pathFile;
    private String inputVarName;

    public WriteFile() {
        super("Write File");
    }

    @Override
    public void execute() {

        if (!ExecutionContext.containsVariable(inputVarName)) {
            throw new IllegalArgumentException("Input variable '" + inputVarName + "' does not exist!");
        }

        Variable inputVar = ExecutionContext.getVariable(inputVarName);

        if (inputVar.getType() != VariableType.STRING && inputVar.getType() != VariableType.LIST_STRING) {
            throw new IllegalArgumentException("Input variable must be of type STRING or LIST_STRING");
        }

        try (FileWriter fileWriter = new FileWriter(pathFile)) {
            if (inputVar.getType() == VariableType.LIST_STRING) {
                @SuppressWarnings("unchecked")
                List<String> inputList = (List<String>) inputVar.getValue();
                String joined = String.join("\n", inputList);
                fileWriter.write(joined);
            } else {
                fileWriter.write(inputVar.getValue().toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("WriteFile: Failed to write to file '" + pathFile + "'", e);
        }
        log();
    }
    
    @Override
    public void configure(Map<String, String> params) {
        this.configData.putAll(params);
        this.pathFile = params.get("pathFile");
        this.inputVarName = params.get("inputVarName");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("pathFile", "inputVarName");
    }

    @Override
    public void log() {
        System.out.println(getName() + " block: " + pathFile + " saved");
    }

    @Override
    public ParamKind getParamKind(String key) {
        return switch (key) {
            case "pathFile" -> ParamKind.STRING;
            case "inputVarName" -> ParamKind.VARIABLE;
            default -> throw new IllegalArgumentException("Unknown config key: " + key);
        };
    }
}