package org.example.workflow;

import com.google.gson.*;
import org.example.variables.ExecutionContext;
import org.example.variables.Variable;
import org.example.variables.VariableType;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowSerializer {
    private final Gson gson;
    private final BlockRegistry blockRegistry;

    public WorkflowSerializer(BlockRegistry blockRegistry) {
        this.blockRegistry = blockRegistry;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(Workflow workflow, String filename) throws IOException {
        Map<String, Object> data = new HashMap<>();

        List<Map<String, Object>> serializedBlocks = new ArrayList<>();
        for (ActionBlock block : workflow.getBlocks()) {
            Map<String, Object> blockData = new HashMap<>();
            blockData.put("name", block.getName());
            blockData.put("config", block.getConfigData());
            serializedBlocks.add(blockData);
        }
        data.put("blocks", serializedBlocks);

        List<Map<String, Object>> serializedVars = new ArrayList<>();
        for (Map.Entry<String, Variable> entry : ExecutionContext.getAllVariables().entrySet()) {
            Variable var = entry.getValue();

            Map<String, Object> varData = new HashMap<>();
            varData.put("name", var.getName());
            varData.put("value", var.getValue());
            varData.put("type", var.getType());

            serializedVars.add(varData);
        }
        data.put("variables", serializedVars);

        try (FileWriter file = new FileWriter(filename)) {
            gson.toJson(data, file);
        }
    }

    public void load(Workflow workflow, String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            JsonObject data = gson.fromJson(reader, JsonObject.class);

            if (data.has("variables")) {
                ExecutionContext.clear();

                JsonArray varArray = data.getAsJsonArray("variables");
                for (JsonElement elem : varArray) {
                    JsonObject varObj = elem.getAsJsonObject();

                    String name = varObj.get("name").getAsString();
                    VariableType type = VariableType.valueOf(varObj.get("type").getAsString());
                    Object value = parseVariableValue(varObj.get("value"), type);

                    ExecutionContext.setVariable(name, type, value);
                }
            }

            if (data.has("blocks")) {
                workflow.clear();

                JsonArray blockArray = data.getAsJsonArray("blocks");
                for (JsonElement elem : blockArray) {
                    JsonObject blockObj = elem.getAsJsonObject();

                    String name = blockObj.get("name").getAsString();
                    JsonObject configObj = blockObj.getAsJsonObject("config");

                    ActionBlock block = blockRegistry.createBlock(name);

                    Map<String, String> params = new HashMap<>();
                    for (String key : configObj.keySet()) {
                        params.put(key, configObj.get(key).getAsString());
                    }

                    block.configure(params);
                    workflow.addBlock(block);
                }
            }
        }
    }

    private Object parseVariableValue(JsonElement valueElem, VariableType type) {
        return switch (type) {
            case NUMBER -> valueElem.getAsDouble();
            case STRING -> valueElem.getAsString();
            case BOOLEAN -> valueElem.getAsBoolean();
            case LIST_NUMBER -> {
                List<Double> list = new ArrayList<>();
                for (JsonElement e : valueElem.getAsJsonArray()) {
                    list.add(e.getAsDouble());
                }
                yield list;
            }
            case LIST_STRING -> {
                List<String> list = new ArrayList<>();
                for (JsonElement e : valueElem.getAsJsonArray()) {
                    list.add(e.getAsString());
                }
                yield list;
            }
        };
    }
}
