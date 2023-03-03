package ru.practicum.models;

import java.util.Comparator;

public class EpicSortingComparator implements Comparator<Subtask> {
    @Override
    public int compare(Subtask s1, Subtask s2) {
        return s1.getStartTime().compareTo(s2.startTime);
    }
}