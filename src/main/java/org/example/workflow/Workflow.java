package org.example.workflow;

import org.example.variables.Variable;
import org.example.variables.ExecutionContext;
import org.example.variables.VariableType;

import java.util.ArrayList;
import java.util.List;

public class Workflow {
    private final List<ActionBlock> blocks;

    public Workflow() {
        blocks = new ArrayList<>();
    }

    public void addBlock(ActionBlock block) {
        blocks.add(block);
    }

    public void removeLastBlock() {
        if (!blocks.isEmpty()) {
            blocks.remove(blocks.size() - 1);
        }
    }


    public void run() {
        if (blocks.isEmpty()) {
            System.out.println("No blocks added yet");
        }
        for (ActionBlock block : blocks) {
            block.execute();
        }
    }

    public void showListblocks() {
        if (blocks.isEmpty()) {
            System.out.println("No blocks added yet");
            return;
        }

        for (ActionBlock block : blocks) {
            System.out.print(block.getName() + " -> ");
        }
        System.out.println("Finish");
    }
}