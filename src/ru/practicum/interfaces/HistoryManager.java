package ru.practicum.interfaces;

import ru.practicum.models.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();
}
