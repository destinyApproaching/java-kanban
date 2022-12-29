package ru.practicum.services;

import ru.practicum.models.Task;

import java.util.List;


public class Manager {
    private static InMemoryHistoryManager inMemoryHistoryManager;

    public static void setInMemoryHistoryManager(InMemoryHistoryManager newInMemoryHistoryManager) {
        inMemoryHistoryManager = newInMemoryHistoryManager;
    }

    public static List<Task> getDefaultHistory() {
        return inMemoryHistoryManager.getHistory();
    }
}
