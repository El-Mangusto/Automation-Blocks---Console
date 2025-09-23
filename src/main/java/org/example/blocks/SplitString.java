package org.example.blocks;

import org.example.variables.ExecutionContext;
import org.example.variables.Variable;
import org.example.variables.VariableType;
import org.example.workflow.ActionBlock;
import org.example.workflow.ParamKind;

import java.util.List;
import java.util.Map;

public class SplitString extends ActionBlock {
    private String delimiter;
    private String inputVarName;
    private String outputVarName;

    public SplitString() {
        super("SplitString");
    }

    @Override
    public void execute() {
        if (!ExecutionContext.containsVariable(inputVarName)) {
            throw new IllegalArgumentException("Input variable '" + inputVarName + "' does not exist!");
        }

        Variable inputVar = ExecutionContext.getVariable(inputVarName);

        if (inputVar.getType() != VariableType.STRING) {
            throw new IllegalArgumentException("Input variable must be of type STRING");
        }

        String str = (String) inputVar.getValue();
        List<String> result = List.of(str.split(delimiter));

        ExecutionContext.setVariable(outputVarName, VariableType.LIST_STRING, result);

        log();
    }

    @Override
    public void configure(Map<String, String> params) {
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
        System.out.println(getName() + " block: split by '" + delimiter + "', result in " + outputVarName);
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