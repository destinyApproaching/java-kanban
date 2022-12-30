package ru.practicum.utils;

import ru.practicum.services.HistoryManager;
import ru.practicum.services.InMemoryHistoryManager;
import ru.practicum.services.InMemoryTaskManager;
import ru.practicum.services.TaskManager;

public class Manager {
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }
}