package ru.practicum.interfaces;

import ru.practicum.models.*;

import java.util.List;

public interface TaskManager {
    void getTasks(); // keep list of tasks
    void deleteAllTasks(); // delete all tasks
    void getTaskById(int id); // keep task by id
    void createTask(Task task); // Creating... Object should send as parameter
    void updateTask(Task task); // Updating... getStatus()
    void deleteTaskById(); // Deleting by id
    void printSubtasksInEpic(); // sout subtask in epic
}
