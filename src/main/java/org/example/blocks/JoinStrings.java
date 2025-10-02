package org.example.blocks;

import org.example.variables.ExecutionContext;
import org.example.variables.Variable;
import org.example.variables.VariableType;
import org.example.workflow.ActionBlock;
import org.example.workflow.ParamKind;

import java.util.List;
import java.util.Map;

public class JoinStrings extends ActionBlock {
    private String delimiter;
    private String inputVarName;
    private String outputVarName;

    public  JoinStrings() {
        super("JoinStrings");
    }

    @Override
    public void execute() {
        if (!ExecutionContext.containsVariable(inputVarName)) {
            throw new IllegalArgumentException("Input variable '" + inputVarName + "' does not exist!");
        }

        Variable inputVar = ExecutionContext.getVariable(inputVarName);

        if (inputVar.getType() != VariableType.LIST_STRING) {
            throw new IllegalArgumentException("Input variable must be of type LIST_STRING");
        }
        List<String> inputList = (List<String>) inputVar.getValue();

        String result = String.join(delimiter, inputList);

        ExecutionContext.setVariable(outputVarName, VariableType.STRING, result);

        log();
    }

    @Override
    public void configure(Map<String, String> params) {
        this.configData.putAll(params);
        this.delimiter = params.get("delimiter");
        this.inputVarName = params.get("inputVarName");
        this.outputVarName = params.get("outputVarName");
    }

    @Override
    public List<String> getConfigKeys() {
        return List.of("delimiter", "inputVarName", "outputVarName");
    }

    @Override
    public void log() {
        System.out.println(getName() + " block: joined " + inputVarName + " using delimiter '" + delimiter );
    }

    @Override
    public ParamKind getParamKind(String key) {
        return switch (key) {
            case "delimiter" -> ParamKind.STRING;
            case "inputVarName", "outputVarName" -> ParamKind.VARIABLE;
            default -> throw new IllegalArgumentException("Unknown config key: " + key);
        };
    }
}
