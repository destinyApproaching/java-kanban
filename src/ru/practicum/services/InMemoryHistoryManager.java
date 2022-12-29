package ru.practicum.services;

import ru.practicum.interfaces.HistoryManager;
import ru.practicum.models.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private final int maxHistorySize = 10;


    @Override
    public void add(Task task) {
        if (history.size() >= maxHistorySize) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

}
