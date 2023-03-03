package ru.practicum.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.models.Epic;
import ru.practicum.models.Subtask;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    Epic epicWithoutSubtask;

    @BeforeEach
    void beforeEach() {
        taskManager = new FileBackedTasksManager(new File("Task.csv"));

        task = new Task(
                "1st Task",
                "first",
                1,
                TaskStatus.NEW
        );

        epic = new Epic(
                "2nd Task",
                "second",
                2
        );

        epic.addSubtask(new Subtask(
                        "3rd Task",
                        "third",
                        3,
                        TaskStatus.NEW,
                        2
                )
        );

        subtask = new Subtask(
                "4th Task",
                "forth",
                4,
                TaskStatus.NEW,
                2
        );

        epic.addSubtask(subtask);
        epic.checker();

        epicWithoutSubtask = new Epic(
                "2nd Task",
                "second",
                2
        );
        epicWithoutSubtask.checker();
    }

    @Test
    void shouldSaveAndLoadFromFileWhenListIsEmpty() {
        taskManager.save();
        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(new File("Task.csv"));
        assertIterableEquals(loader.getTasks(), taskManager.getTasks());
    }

    @Test
    void shouldSaveWhenEpicWithoutSubtask() {
        assertEquals(TaskStatus.NEW, epicWithoutSubtask.getTaskStatus());
    }

    @Test
    void shouldSaveAndLoadFromFileWhenHistoryListIsEmpty() {
        taskManager.save();
        FileBackedTasksManager loader = FileBackedTasksManager.loadFromFile(new File("Task.csv"));
        assertIterableEquals(loader.getHistory(), taskManager.getHistory());
    }
}