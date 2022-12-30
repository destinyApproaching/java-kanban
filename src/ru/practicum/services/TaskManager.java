package ru.practicum.services;

import ru.practicum.models.*;
import ru.practicum.services.InMemoryHistoryManager;

import java.util.List;

public interface TaskManager {
    InMemoryHistoryManager inMemoryHistoryManager = null;

    Task getTask(int id);

    Task getEpic(int id);

    int getEpicId(int id);

    int getCurrent();

    void getTasks();

    void deleteAllTasks();

    void getTaskById(int id);

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTaskById();

    void printSubtasksInEpic();

    List<Task> getHistory();
}
