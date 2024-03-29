package ru.practicum.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String taskName, String description, int taskId, TaskStatus taskStatus, Long duration, LocalDateTime startTime, int epicId) {
        super(taskName, description, taskId, taskStatus, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String description, int taskId, TaskStatus taskStatus, int epicId) {
        super(taskName, description, taskId, taskStatus);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                ", taskDuration=" + taskDuration +
                ", startTime=" + startTime +
                '}';
    }
}
