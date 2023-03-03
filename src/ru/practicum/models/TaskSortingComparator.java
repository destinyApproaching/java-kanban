package ru.practicum.models;

import java.util.Comparator;

public class TaskSortingComparator implements Comparator<Task> {
    @Override
    public int compare(Task s1, Task s2) {
        return s1.getStartTime().compareTo(s2.startTime);
    }
}