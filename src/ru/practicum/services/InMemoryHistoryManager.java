package ru.practicum.services;

import ru.practicum.models.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new LinkedList<>();
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
