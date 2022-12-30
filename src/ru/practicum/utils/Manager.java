package ru.practicum.utils;

import ru.practicum.models.Task;
import ru.practicum.services.InMemoryTaskManager;
import ru.practicum.services.TaskManager;

import java.util.List;

public class Manager {
    private static TaskManager inMemoryTaskManager = null;

    public static List<Task> getDefaultHistory() {
        if (inMemoryTaskManager == null) {
            System.out.println("Вы не создали TaskManager");
            return null;
        } else return inMemoryTaskManager.getHistory();
    }

    public static TaskManager getDefaultTaskManager() {
        inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }
}
