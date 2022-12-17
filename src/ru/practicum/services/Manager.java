package ru.practicum.services;


import ru.practicum.interfaces.TaskManager;
import ru.practicum.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager implements TaskManager {
    private int uniqueId = 1;
    Scanner scanner = new Scanner(System.in);
    private final List<Task> tasks;

    public Manager() {
        this.tasks = new ArrayList<>();
    }

    public int getUniqueId() {
        return uniqueId;
    }

    private void changeUniqueId() {
        this.uniqueId++;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getEpicId(int id) {
        return tasks.get(id).getTaskId();
    }

    public Task getEpic(int id) {
        return tasks.get(id);
    }

    @Override
    public void getTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("Вывожу задачи:");
            for (Task task : tasks) {
                System.out.println(task.toString());
            }
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        setUniqueId(1);
        System.out.println("Удаление прошло успешно.");
    }

    @Override
    public void getTaskById(int id) {
        boolean flag = false;
        for (Task task : tasks) {
            if (task.getTaskId() == id) {
                System.out.println(task);
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Под таким id нет задач");
        }
    }

    @Override
    public void createTask(Task task) {
        tasks.add(task);
        changeUniqueId();
    }

    @Override
    public void updateTask(Task task) {
        System.out.println("Всего id: " + getUniqueId());
        System.out.println("Введите id, которое хотите обновить");
        int id = scanner.nextInt();
        tasks.add(id, task);
    }

    @Override
    public void deleteTaskById() {
        System.out.println("Всего id: " + getUniqueId());
        System.out.println("Введите id, которое хотите удалить");
        int id = scanner.nextInt();
        tasks.remove(id);
    }

    @Override
    public void printSubtasksInEpic() {
        System.out.println("Всего id: " + getUniqueId());
        System.out.println("Введите id Epic-класса");
        int id = scanner.nextInt();
        if (tasks.get(id).getClass() == Epic.class) {
            System.out.println(((Epic) tasks.get(id)).getSubtasks());
        } else System.out.println("Под таким id не Epic-класс");
    }
}