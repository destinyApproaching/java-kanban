package ru.practicum.services;

import ru.practicum.models.*;

import java.util.List;

public interface TaskManager {

    Task getTask(int id);

    Task getEpic(int id);

    Task getSubtask(int id);

    int getEpicId(int id);

    List<Task> getTasks();

    void deleteAllTasks();

    Task getTaskById(int id);

    void createTask(Task task);

    void deleteTaskById(int id);

    List<Subtask> printSubtasksInEpic(int id);

    List<Task> getHistory();

    int getCurrent();

    HistoryManager getHistoryManager();
}
