package org.example;

import org.example.cli.ConsoleInterface;
import org.example.workflow.Workflow;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Workflow workflow = new Workflow();
        ConsoleInterface console = new ConsoleInterface(scanner, workflow);
    }
}

// Починить все блоки для работы с переменными, добавить новые блоки, штучек 5-10.
// Сделать возможность сохранять цепочки и загружать
// Делать интерфейс(((