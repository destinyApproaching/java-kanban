package ru.practicum.services;

import ru.practicum.models.*;
import ru.practicum.utils.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {
    private int current = 1;
    Scanner scanner = new Scanner(System.in);
    private final List<Task> tasks = new ArrayList<>();
    private final HistoryManager inMemoryHistoryManager = Manager.getDefaultHistory();

    @Override
    public int getCurrent() {
        return current;
    }

    private void getNextCurrent() {
        this.current++;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public int getEpicId(int id) {
        return tasks.get(id).getTaskId();
    }

    @Override
    public Task getEpic(int id) {
        return tasks.get(id);
    }

    @Override
    public Task getTask(int id) {
        inMemoryHistoryManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task getSubtask(int id) {
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
        for (int i = 1; i <= getCurrent() ; i++) {
            inMemoryHistoryManager.remove(i);
        }
        tasks.clear();
        setCurrent(1);
        System.out.println("Удаление прошло успешно.");
    }

    @Override
    public void getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getTaskId() == id) {
                System.out.println(task);
                return;
            }
        }
        System.out.println("Под таким id нет задач");
    }

    @Override
    public void createTask(Task task) {
        tasks.add(task);
        getNextCurrent();
    }

    @Override
    public void updateTask(Task task) {
        System.out.println("Всего id: " + getCurrent());
        System.out.println("Введите id, которое хотите обновить");
        int id = scanner.nextInt();
        tasks.add(id, task);
    }

    @Override
    public void deleteTaskById() {
        System.out.println("Введите id, которое хотите удалить");
        int id = scanner.nextInt();
        for (Task task : tasks) {
            if (task.getTaskId() == id) {
                tasks.remove(task);
                inMemoryHistoryManager.remove(task.getTaskId());
                if (task.getClass().getSimpleName().equals("Epic")) {
                    for (Task subtask : ((Epic) task).getSubtasks()) {
                        tasks.remove(subtask);
                        inMemoryHistoryManager.remove(subtask.getTaskId());
                    }
                }
            }
        }
    }

    @Override
    public void printSubtasksInEpic() {
        System.out.println("Введите id Epic-класса");
        int id = scanner.nextInt();
        if (tasks.get(id).getClass() == Epic.class) {
            System.out.println(((Epic) tasks.get(id)).getSubtasks());
        } else System.out.println("Под таким id не Epic-класс");
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }
}
