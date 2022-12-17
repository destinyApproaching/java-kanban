package ru.practicum;

import ru.practicum.models.*;
import ru.practicum.services.Manager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        Task task;
        // 1
        String name = scanner.nextLine();
        String description = scanner.nextLine();
        String status = scanner.nextLine();
        task = new Task(name, description, manager.getUniqueId(), status);
        manager.createTask(task);
        // 2
        name = scanner.nextLine();
        description = scanner.nextLine();
        status = scanner.nextLine();
        task = new Task(name, description, manager.getUniqueId(), status);
        manager.createTask(task);
        // 3
        name = scanner.nextLine();
        description = scanner.nextLine();
        status = scanner.nextLine();
        task = new Epic(name, description, manager.getUniqueId(), status);
        manager.createTask(task);
        // 4
        name = scanner.nextLine();
        description = scanner.nextLine();
        status = scanner.nextLine();
        task = new Subtask(name, description, manager.getUniqueId(), status);
        ((Subtask) task).setEpicId(manager.getEpicId(2));
        manager.createTask(task);
        ((Epic) manager.getEpic(2)).addSubtask((Subtask) task);
        // 5
        name = scanner.nextLine();
        description = scanner.nextLine();
        status = scanner.nextLine();
        task = new Subtask(name, description, manager.getUniqueId(), status);
        ((Subtask) task).setEpicId(manager.getEpicId(2));
        manager.createTask(task);
        ((Epic) manager.getEpic(2)).addSubtask((Subtask) task);
        // 6
        name = scanner.nextLine();
        description = scanner.nextLine();
        status = scanner.nextLine();
        task = new Epic(name, description, manager.getUniqueId(), status);
        manager.createTask(task);
        // 7
        name = scanner.nextLine();
        description = scanner.nextLine();
        status = scanner.nextLine();
        task = new Subtask(name, description, manager.getUniqueId(), status);
        ((Subtask) task).setEpicId(manager.getEpicId(5));
        manager.createTask(task);
        ((Epic) manager.getEpic(5)).addSubtask((Subtask) task);

        manager.getTasks();
        manager.printSubtasksInEpic();
        manager.deleteTaskById();
    }
}
