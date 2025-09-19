package org.example.workflow;

import org.reflections.Reflections;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BlockRegistry {
    private final Map<String, Class<? extends ActionBlock>> blocks = new LinkedHashMap<>();

    public BlockRegistry(String packageName) {
        loadBlocks(packageName);
    }

    private void loadBlocks(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends ActionBlock>> blockClasses = reflections.getSubTypesOf(ActionBlock.class);

        for (Class<? extends ActionBlock> blockClass : blockClasses) {
            try {
                ActionBlock block = blockClass.getDeclaredConstructor().newInstance();
                blocks.put(block.getName(), blockClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Set<String> getBlockNames() {
        return blocks.keySet();
    }

    public ActionBlock createBlock(String name) {
        Class<? extends ActionBlock> blockClass = blocks.get(name);
        if (blockClass == null) {
            throw new IllegalArgumentException("Unknown block: " + name);
        }
        try {
            return blockClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create block: " + name, e);
        }
    }
}
