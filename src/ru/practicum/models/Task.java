package ru.practicum.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String taskName;
    protected String description;
    protected int taskId;
    protected TaskStatus taskStatus;
    protected Long taskDuration = null;
    protected LocalDateTime startTime = null;

    public Task(String taskName, String description, int taskId) {
        this.taskName = taskName;
        this.description = description;
        this.taskId = taskId;
    }



    public Task(String taskName, String description, int taskId, TaskStatus taskStatus, Long duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.description = description;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        this.taskDuration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String description, int taskId, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.description = description;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public Long getTaskDuration() {
        return taskDuration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (taskDuration == null || startTime == null) {
            return null;
        } else {
            return startTime.plusMinutes(taskDuration);
        }
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
                ", taskStatus=" + taskStatus +
                ", taskDuration=" + taskDuration +
                ", startTime=" + startTime +
                '}';
    }
}
