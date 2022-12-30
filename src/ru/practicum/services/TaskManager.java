package ru.practicum.services;

import ru.practicum.models.*;
import ru.practicum.services.InMemoryHistoryManager;

import java.util.List;

public interface TaskManager {

    Task getTask(int id);

    Task getEpic(int id);

    Task getSubtask(int id);

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
