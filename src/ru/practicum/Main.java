package ru.practicum;

import ru.practicum.models.*;
import ru.practicum.services.InMemoryTaskManager;
import ru.practicum.enums.TaskStatus;
import ru.practicum.services.Manager;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Subtask subtask;
        inMemoryTaskManager.createTask(new Task("Помыть посуду", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 1

        inMemoryTaskManager.createTask(new Epic("Помыть полы", "Взять губку",
                inMemoryTaskManager.getCurrent())); // 2

        inMemoryTaskManager.createTask(subtask = new Subtask("Купить швабру", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE, 1)); // 3
        ((Epic) inMemoryTaskManager.getEpic(1)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(1)).checker();

        inMemoryTaskManager.createTask(subtask = new Subtask("Купить таз", "Взять губку",
                inMemoryTaskManager.getCurrent(), TaskStatus.DONE, 1)); // 4
        ((Epic) inMemoryTaskManager.getEpic(1)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(1)).checker();

        inMemoryTaskManager.createTask(new Epic("Приготовить бутерброд", "Взять губку",
                inMemoryTaskManager.getCurrent())); // 5

        inMemoryTaskManager.createTask(subtask = new Subtask("Порезать колбасу",
                "Взять губку", inMemoryTaskManager.getCurrent(), TaskStatus.NEW, 4)); //6
        ((Epic) inMemoryTaskManager.getEpic(4)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(4)).checker();

        inMemoryTaskManager.createTask(subtask = new Subtask("Положить колбасу на хлеб",
                "Взять губку", inMemoryTaskManager.getCurrent(), TaskStatus.IN_PROGRESS, 4)); // 6
        ((Epic) inMemoryTaskManager.getEpic(4)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(4)).checker();

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
                inMemoryTaskManager.getCurrent(), TaskStatus.NEW)); // 21

        System.out.println(inMemoryTaskManager.getTask(20));
        System.out.println(inMemoryTaskManager.getTask(19));
        System.out.println(inMemoryTaskManager.getTask(18));
        System.out.println(inMemoryTaskManager.getTask(17));
        System.out.println(inMemoryTaskManager.getTask(16));
        System.out.println(inMemoryTaskManager.getTask(15));
        System.out.println(inMemoryTaskManager.getTask(14));
        System.out.println(inMemoryTaskManager.getTask(13));
        System.out.println(inMemoryTaskManager.getTask(12));
        System.out.println(inMemoryTaskManager.getTask(11));
        System.out.println(inMemoryTaskManager.getTask(10));
        System.out.println(inMemoryTaskManager.getTask(9));
        System.out.println(inMemoryTaskManager.getTask(8));
        System.out.println(inMemoryTaskManager.getTask(7));

        System.out.println(inMemoryTaskManager.inMemoryHistoryManager.getHistory());
        Manager.setInMemoryHistoryManager(inMemoryTaskManager.inMemoryHistoryManager);
        System.out.println(Manager.getDefaultHistory());

    }
}
