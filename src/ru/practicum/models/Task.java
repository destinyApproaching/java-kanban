package ru.practicum.models;

import java.util.Objects;

public class Task {
    protected String taskName;
    protected String description;
    protected int taskId = 0;
    protected TaskStatus taskStatus; // NEW || IN_PROGRESS || DONE

    public Task(String taskName, String description, int taskId) {
        this.taskName = taskName;
        this.description = description;
        this.taskId = taskId;
    }

    public Task(String taskName, String description, int taskId, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.description = description;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public int getTaskId() {
        return taskId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && Objects.equals(taskStatus, task.taskStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, taskId, taskStatus);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
