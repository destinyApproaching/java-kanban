package ru.practicum.services;

import ru.practicum.models.*;

import java.util.List;
import java.util.Set;

public interface  TaskManager {

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    int getEpicId(int id);

    List<Task> getTasks();

    void deleteAllTasks();

    Task getTaskById(int id);

    void createTask(Task task);

    void updateTask(Task task, int id);

    void deleteTaskById(int id);

    List<Subtask> printSubtasksInEpic(int id);

    List<Task> getHistory();

    int getCurrent();

    Set<Task> getPrioritizedTasks();

    HistoryManager getHistoryManager();
}
