package ru.practicum.services;

import org.junit.jupiter.api.BeforeEach;
import ru.practicum.models.Epic;
import ru.practicum.models.Subtask;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();

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
    }
}