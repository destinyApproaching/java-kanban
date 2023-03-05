package ru.practicum.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Subtask> subtasks;
    private final EpicSortingComparator epicSortingComparator = new EpicSortingComparator();
    public Epic(String taskName, String description,
                int taskId, TaskStatus taskStatus) {
        super(taskName, description, taskId, taskStatus);
        this.subtasks = new ArrayList<>();
    }

    public Epic(String taskName, String description, int taskId) {
        super(taskName, description, taskId);
        this.subtasks = new ArrayList<>();
    }

    public Epic(String taskName, String description, int taskId, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        super(taskName, description, taskId, taskStatus, duration, startTime);
        this.subtasks = new ArrayList<>();
    }

    public void checker() {
        int newCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.taskStatus == TaskStatus.NEW) {
                newCount++;
            }
            if (subtask.taskStatus == TaskStatus.DONE) {
                doneCount++;
            }
        }
        if (subtasks.isEmpty() || newCount == subtasks.size()) {
            taskStatus = TaskStatus.NEW;
        } else if (doneCount == subtasks.size()) {
            taskStatus = TaskStatus.DONE;
        } else taskStatus = TaskStatus.IN_PROGRESS;
        taskDuration = null;
        startTime = null;
    }

    public void checkerWithDurationAndDateTime() {
        int newCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.taskStatus == TaskStatus.NEW) {
                newCount++;
            }
            if (subtask.taskStatus == TaskStatus.DONE) {
                doneCount++;
            }
        }
        if (subtasks.isEmpty() || newCount == subtasks.size()) {
            taskStatus = TaskStatus.NEW;
        } else if (doneCount == subtasks.size()) {
            taskStatus = TaskStatus.DONE;
        } else taskStatus = TaskStatus.IN_PROGRESS;
        subtasks.sort(epicSortingComparator);
        taskDuration = getSumOfDuration();
        startTime = subtasks.get(0).startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        subtasks.sort(epicSortingComparator);
        return subtasks.get(subtasks.size() - 1).startTime.plus(subtasks.get(subtasks.size() - 1).getTaskDuration());
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
                ", taskStatus=" + taskStatus +
                ", taskDuration=" + taskDuration +
                ", startTime=" + startTime +
                '}';
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public Duration getSumOfDuration() {
        Duration sumOfDuration = Duration.ofMinutes(0);
        for (Subtask subtask : subtasks) {
            sumOfDuration = sumOfDuration.plus(subtask.getTaskDuration());
        }
        return sumOfDuration;
    }
}