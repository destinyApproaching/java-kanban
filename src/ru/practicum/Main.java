package ru.practicum;

import ru.practicum.services.TaskManager;
import ru.practicum.models.*;
import ru.practicum.models.TaskStatus;
import ru.practicum.utils.Manager;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Manager.getDefaultTaskManager();
        Subtask subtask;

        inMemoryTaskManager.createTask(new Task("Помыть посуду", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 1

        inMemoryTaskManager.createTask(new Task("Постирать вещи", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.IN_PROGRESS)); // 2


        inMemoryTaskManager.createTask(new Epic("Помыть полы", "Взять губку",
                inMemoryTaskManager.getCurrent())); // 3

        inMemoryTaskManager.createTask(subtask = new Subtask("Купить швабру", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE, 2)); // 4
        ((Epic) inMemoryTaskManager.getEpic(2)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(2)).checker();

        inMemoryTaskManager.createTask(subtask = new Subtask("Купить таз", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE, 2)); // 5
        ((Epic) inMemoryTaskManager.getEpic(2)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(2)).checker();

        inMemoryTaskManager.createTask(subtask = new Subtask("Порезать колбасу",
                "Взять губку", inMemoryTaskManager.getCurrent(), TaskStatus.NEW, 2)); // 6
        ((Epic) inMemoryTaskManager.getEpic(2)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(2)).checker();

        inMemoryTaskManager.createTask(new Epic("Приготовить бутерброд", "Взять губку",
                inMemoryTaskManager.getCurrent())); // 7
        ((Epic) inMemoryTaskManager.getEpic(6)).checker();

/*        inMemoryTaskManager.createTask(subtask = new Subtask("Порезать колбасу",
                "Взять губку", inMemoryTaskManager.getCurrent(), TaskStatus.NEW, 6)); //
        ((Epic) inMemoryTaskManager.getEpic(6)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(6)).checker();

        inMemoryTaskManager.createTask(subtask = new Subtask("Положить колбасу на хлеб",
                "Взять губку", inMemoryTaskManager.getCurrent(), TaskStatus.IN_PROGRESS, 6)); // 6
        ((Epic) inMemoryTaskManager.getEpic(6)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(6)).checker();

        inMemoryTaskManager.createTask(new Task("Постирать вещи", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.IN_PROGRESS)); // 7
        inMemoryTaskManager.createTask(new Task("Почистить зубы", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE)); // 8
        inMemoryTaskManager.createTask(new Task("Погулять", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 9
        inMemoryTaskManager.createTask(new Task("Посмотреть телевизор", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE)); // 10
        inMemoryTaskManager.createTask(new Task("Сделать домашку", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE)); // 11
        inMemoryTaskManager.createTask(new Task("Пойти в школу", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 12
        inMemoryTaskManager.createTask(new Task("Посетить ВУЗ", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.IN_PROGRESS)); // 13
        inMemoryTaskManager.createTask(new Task("Купить новую печку", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.IN_PROGRESS)); // 14
        inMemoryTaskManager.createTask(new Task("Выгулять собаку", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 15
        inMemoryTaskManager.createTask(new Task("Заказать суши", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE)); // 16
        inMemoryTaskManager.createTask(new Task("Поиграть в колду", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE)); // 17
        inMemoryTaskManager.createTask(new Task("Выпить сок", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 18
        inMemoryTaskManager.createTask(new Task("Помыться", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 19
        inMemoryTaskManager.createTask(new Task("Заправить кровать", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 20
        inMemoryTaskManager.createTask(new Task("Сходить в парк", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 21*/

        System.out.println(inMemoryTaskManager.getTask(0));
        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getTask(3));
        System.out.println(inMemoryTaskManager.getTask(4));
        System.out.println(inMemoryTaskManager.getTask(5));
        System.out.println(inMemoryTaskManager.getTask(6));
        System.out.println(inMemoryTaskManager.getTask(6));
        System.out.println(inMemoryTaskManager.getTask(5));
        System.out.println(inMemoryTaskManager.getTask(4));
        System.out.println(inMemoryTaskManager.getTask(3));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println(inMemoryTaskManager.getTask(0));

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");


        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.deleteTaskById();
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.deleteAllTasks();
        System.out.println(inMemoryTaskManager.getHistory());
    }
}
