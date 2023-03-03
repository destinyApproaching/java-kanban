import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.models.Epic;
import ru.practicum.models.Subtask;
import ru.practicum.models.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    Epic epic;

    @Test
    public void shouldEpicIsNewWhenArrayIsBlank() {
        epic = new Epic(
                "Помыть посуду",
                "1",
                1
        );
        epic.checker();
        Assertions.assertEquals(
                TaskStatus.NEW,
                epic.taskStatus
        );
    }

    @Test
    public void shouldEpicIsNewWhenAllSubtaskIsNew() {
        epic = new Epic(
                "Помыть посуду",
                "1",
                1
        );
        epic.addSubtask(new Subtask(
                "Взять тряпку",
                "2",
                2,
                TaskStatus.NEW,
                1
                )
        );
        epic.checker();
        assertEquals(
                TaskStatus.NEW,
                epic.getTaskStatus()
        );
    }

    @Test
    public void shouldEpicIsDoneWhenAllSubtaskIsDone() {
        epic = new Epic(
                "Помыть посуду",
                "1",
                1
        );
        epic.addSubtask(new Subtask(
                "Взять тряпку",
                "2",
                2,
                TaskStatus.DONE,
                1
                )
        );
        epic.checker();
        assertEquals(
                TaskStatus.DONE,
                epic.getTaskStatus()
        );
    }

    @Test
    public void shouldEpicIsInProgressWhenAllSubtaskIsNewAndIsDone() {
        epic = new Epic(
                "Помыть посуду",
                "1",
                1
        );
        epic.addSubtask(new Subtask(
                "Взять тряпку",
                "2",
                2,
                TaskStatus.NEW,
                1
                )
        );
        epic.addSubtask(new Subtask(
                "Включить воду",
                "3",
                3,
                TaskStatus.DONE,
                1
                )
        );
        epic.checker();
        assertEquals(
                TaskStatus.IN_PROGRESS,
                epic.getTaskStatus()
        );
    }

    @Test
    public void shouldEpicIsInProgressWhenAllSubtaskIsInProgress() {
        epic = new Epic(
                "Помыть посуду",
                "1",
                1
        );
        epic.addSubtask(new Subtask(
                "Взять тряпку",
                "2",
                2,
                TaskStatus.IN_PROGRESS,
                1
                )
        );
        epic.checker();
        assertEquals(
                TaskStatus.IN_PROGRESS,
                epic.getTaskStatus()
        );
    }

    @Test
    public void shouldGetEndTime() {
        epic = new Epic("1", "1", 1);
        epic.addSubtask(new Subtask("2", "2", 2, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 11, 27, 4, 30), 1));
        epic.addSubtask(new Subtask("3", "3", 3, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 12, 27, 4, 30), 1));
        epic.addSubtask(new Subtask("4", "4", 4, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 10, 27, 4, 30), 1));
        epic.checkerWithDurationAndDateTime();
        assertEquals(LocalDateTime.of(2001, 12, 27, 5, 0), epic.getEndTime());
    }

    @Test
    public void shouldSortSubtasks() {
        epic = new Epic("1", "1", 1);
        epic.addSubtask(new Subtask("2", "2", 2, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 11, 27, 4, 30), 1));
        epic.addSubtask(new Subtask("3", "3", 3, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 12, 27, 4, 30), 1));
        epic.addSubtask(new Subtask("4", "4", 4, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 10, 27, 4, 30), 1));
        epic.checkerWithDurationAndDateTime();
        List<Integer> ids = new ArrayList<>();
        for (Subtask subtask : epic.getSubtasks()) {
            ids.add(subtask.getTaskId());
        }
        assertEquals("[4, 2, 3]", ids.toString());
    }
}