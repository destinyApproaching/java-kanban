package ru.practicum.models;

import ru.practicum.enums.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Subtask> subtasks;

    public Epic(String taskName, String description, int taskId, TaskStatus taskStatus) {
        super(taskName, description, taskId, taskStatus);
        this.subtasks = new ArrayList<>();
    }

    public void checker() {
        int newCount = 0;
        int inProgressCount = 0;
        int doneCount = 0;
        for (Subtask subtask: subtasks) {
            if (subtask.taskStatus == TaskStatus.NEW) {
                newCount++;
            }
            if (subtask.taskStatus == TaskStatus.IN_PROGRESS) {
                inProgressCount++;
            }
            if (subtask.taskStatus == TaskStatus.DONE) {
                doneCount++;
            }
        }
        if (newCount == subtasks.size() || subtasks.isEmpty()) {
            taskStatus = TaskStatus.NEW;
        } else if (doneCount == subtasks.size()) {
            taskStatus = TaskStatus.DONE;
        } else taskStatus = TaskStatus.IN_PROGRESS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }
}
