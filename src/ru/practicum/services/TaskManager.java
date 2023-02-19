package ru.practicum.services;

import ru.practicum.models.*;
import ru.practicum.services.InMemoryHistoryManager;

import java.util.List;

public interface TaskManager {

    Task getTask(int id) throws ManagerSaveException;

    Task getEpic(int id);

    Task getSubtask(int id);

    int getEpicId(int id);

    int getCurrent();

    List<Task> getTasks();

    void deleteAllTasks() throws ManagerSaveException;

    void getTaskById(int id);

    void createTask(Task task) throws ManagerSaveException;

    void updateTask(Task task);

    void deleteTaskById() throws ManagerSaveException;

    void printSubtasksInEpic();

    List<Task> getHistory();
}
