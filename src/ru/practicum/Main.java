package ru.practicum;

import ru.practicum.models.*;
import ru.practicum.services.Manager;
import ru.practicum.enums.TaskStatus;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        Task task;
        // 1
        String name = scanner.nextLine();
        String description = scanner.nextLine();
        String status;
        label4:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Task(name, description, manager.getCurrent(), TaskStatus.NEW);
                    manager.createTask(task);
                    break label4;
                case "2":
                    task = new Task(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    manager.createTask(task);
                    break label4;
                case "3":
                    task = new Task(name, description, manager.getCurrent(), TaskStatus.DONE);
                    manager.createTask(task);
                    break label4;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }

        // 2
        name = scanner.nextLine();
        description = scanner.nextLine();
        label5:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Task(name, description, manager.getCurrent(), TaskStatus.NEW);
                    manager.createTask(task);
                    break label5;
                case "2":
                    task = new Task(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    manager.createTask(task);
                    break label5;
                case "3":
                    task = new Task(name, description, manager.getCurrent(), TaskStatus.DONE);
                    manager.createTask(task);
                    break label5;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }
        // 3
        name = scanner.nextLine();
        description = scanner.nextLine();
        label6:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Epic(name, description, manager.getCurrent(), TaskStatus.NEW);
                    manager.createTask(task);
                    break label6;
                case "2":
                    task = new Epic(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    manager.createTask(task);
                    break label6;
                case "3":
                    task = new Epic(name, description, manager.getCurrent(), TaskStatus.DONE);
                    manager.createTask(task);
                    break label6;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }
        ((Epic) manager.getEpic(2)).checker();
        // 4
        name = scanner.nextLine();
        description = scanner.nextLine();
        label:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.NEW);
                    ((Subtask) task).setEpicId(manager.getEpicId(2));
                    manager.createTask(task);
                    break label;
                case "2":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    ((Subtask) task).setEpicId(manager.getEpicId(2));
                    manager.createTask(task);
                    break label;
                case "3":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.DONE);
                    ((Subtask) task).setEpicId(manager.getEpicId(2));
                    manager.createTask(task);
                    break label;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }
        ((Epic) manager.getEpic(2)).addSubtask((Subtask) task);
        ((Epic) manager.getEpic(2)).checker();
        // 5
        name = scanner.nextLine();
        description = scanner.nextLine();
        label1:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.NEW);
                    ((Subtask) task).setEpicId(manager.getEpicId(2));
                    manager.createTask(task);
                    break label1;
                case "2":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    ((Subtask) task).setEpicId(manager.getEpicId(2));
                    manager.createTask(task);
                    break label1;
                case "3":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.DONE);
                    ((Subtask) task).setEpicId(manager.getEpicId(2));
                    manager.createTask(task);
                    break label1;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }
        ((Epic) manager.getEpic(2)).addSubtask((Subtask) task);
        ((Epic) manager.getEpic(2)).checker();
        // 6
        name = scanner.nextLine();
        description = scanner.nextLine();
        label2:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Epic(name, description, manager.getCurrent(), TaskStatus.NEW);
                    manager.createTask(task);
                    break label2;
                case "2":
                    task = new Epic(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    manager.createTask(task);
                    break label2;
                case "3":
                    task = new Epic(name, description, manager.getCurrent(), TaskStatus.DONE);
                    manager.createTask(task);
                    break label2;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }
        ((Epic) manager.getEpic(5)).checker();
        // 7
        name = scanner.nextLine();
        description = scanner.nextLine();
        label3:
        while (true) {
            System.out.println("Выберите статус задачи:\n1 - NEW\n2 - IN_PROGRESS\n3 - DONE");
            status = scanner.nextLine();
            switch (status) {
                case "1":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.NEW);
                    ((Subtask) task).setEpicId(manager.getEpicId(5));
                    manager.createTask(task);
                    break label3;
                case "2":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.IN_PROGRESS);
                    ((Subtask) task).setEpicId(manager.getEpicId(5));
                    manager.createTask(task);
                    break label3;
                case "3":
                    task = new Subtask(name, description, manager.getCurrent(), TaskStatus.DONE);
                    ((Subtask) task).setEpicId(manager.getEpicId(5));
                    manager.createTask(task);
                    break label3;
                default:
                    System.out.println("Повторите попытку.");
                    break;
            }
        }
        ((Epic) manager.getEpic(5)).addSubtask((Subtask) task);
        ((Epic) manager.getEpic(5)).checker();

        manager.getTasks();
        manager.printSubtasksInEpic();
        manager.deleteTaskById();
    }
}
