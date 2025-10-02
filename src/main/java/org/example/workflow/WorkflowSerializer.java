package org.example.workflow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        List<Map<String, Object>> serialized = new ArrayList<>();

        for (ActionBlock block : workflow.getBlocks()) {
            Map<String, Object> blockData = new HashMap<>();
            blockData.put("name", block.getName());
            blockData.put("config", block.getConfigData());
            serialized.add(blockData);
        }

        try(FileWriter file = new FileWriter(filename)) {
            gson.toJson(serialized, file);
        }
    }

    public void load(Workflow workflow, String filename) throws IOException {

    }
}
