package org.example.cli;

import org.example.variables.ExecutionContext;
import org.example.variables.VariableType;
import org.example.workflow.ActionBlock;
import org.example.workflow.BlockRegistry;
import org.example.workflow.Workflow;

import java.util.*;

public class ConsoleInterface {
    private final Workflow workflow;
    private final BlockRegistry blockRegistry;

    public ConsoleInterface(Scanner scanner, Workflow workflow) {
        this.workflow = workflow;
        blockRegistry = new BlockRegistry("org.example.blocks");
        mainMenu(scanner);
    }

    public void mainMenu(Scanner scanner) {
        while (true) {
            workflow.showListblocks();
            ExecutionContext.showVariables();

            System.out.println("\n\tMenu:");
            System.out.println("1.Add block");
            System.out.println("2.Remove last block");
            System.out.println("3.Create a variable");
            System.out.println("4.Run");
            System.out.println("5.Exit");

            System.out.print("Enter your choice: ");
            String line = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> addBlock(scanner);
                case 2 -> workflow.removeLastBlock();
                case 3 -> {
                    System.out.print("Name variable: ");
                    String name = scanner.nextLine();

                    System.out.println("0.Number \n1.String \n2.Boolean \n3.List_Number \n4.List_String\n\nType variable: ");
                    int typeChoice = Integer.parseInt(scanner.nextLine().trim());

                    VariableType type = switch (typeChoice) {
                        case 0 -> VariableType.NUMBER;
                        case 1 -> VariableType.STRING;
                        case 2 -> VariableType.BOOLEAN;
                        case 3 -> VariableType.LIST_NUMBER;
                        case 4 -> VariableType.LIST_STRING;
                        default -> throw new IllegalArgumentException("Invalid type");
                    };

                    Object value = readVariableValue(scanner, type);
                    ExecutionContext.setVariable(name, type, value);
                    System.out.println("Variable " + name + " created successfully.\n");
                }
                case 4 -> {
                    System.out.println("\n\n");
                    workflow.run();
                    System.out.println("\n\n");
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addBlock(Scanner scanner) {
        System.out.println("Available blocks:");
        int i = 1;
        List<String> names = new ArrayList<>(blockRegistry.getBlockNames());
        for (String name : names) {
            System.out.println(i++ + ". " + name);
        }

        System.out.print("Choose block: ");
        String line = scanner.nextLine();
        int choice;
        try {
            choice = Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Enter a number.");
            return;
        }

        if (choice < 1 || choice > names.size()) {
            System.out.println("Invalid choice");
            return;
        }

        String blockName = names.get(choice - 1);
        ActionBlock block = blockRegistry.createBlock(blockName);

        Map<String, String> params = new HashMap<>();
        System.out.println("\tConfig block:");
        for (String key : block.getConfigKeys()) {
            System.out.print(key + ": ");
            String value = scanner.nextLine();
            params.put(key, value);
        }
        block.configure(params);

        workflow.addBlock(block);
        System.out.println("Added block: " + block.getName());
    }

    private Object readVariableValue(Scanner scanner, VariableType type) {
        switch (type) {
            case NUMBER -> {
                System.out.print("Enter number: ");
                return Double.parseDouble(scanner.nextLine().trim());
            }
            case STRING -> {
                System.out.print("Enter string: ");
                return scanner.nextLine();
            }
            case BOOLEAN -> {
                System.out.print("Enter boolean (true/false): ");
                return Boolean.parseBoolean(scanner.nextLine().trim());
            }
            case LIST_NUMBER -> {
                System.out.print("Enter numbers separated by space: ");
                String[] parts = scanner.nextLine().trim().split("\\s+");
                List<Double> list = new ArrayList<>();
                for (String p : parts) list.add(Double.parseDouble(p));
                return list;
            }
            case LIST_STRING -> {
                System.out.print("Enter strings separated by comma: ");
                String line = scanner.nextLine().trim();
                return Arrays.asList(line.split("\\s*,\\s*"));
            }
            default -> throw new IllegalArgumentException("Unsupported type");
        }
    }
}
