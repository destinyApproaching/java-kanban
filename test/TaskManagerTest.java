import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.practicum.models.Epic;
import ru.practicum.models.Subtask;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;
import ru.practicum.services.TaskManager;

import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected static Task task;
    protected static Epic epic;
    protected static Subtask subtask;

    @Test
    void shouldGetTaskWhenListIsNotEmpty(){
        taskManager.createTask(task);
        assertEquals(task, taskManager.getTask(0));
    }

    @Test
    public void shouldGetTaskWhenListIsEmpty() {
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getTask(0));

        assertEquals("Index " + 0 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    public void shouldGetTaskWhenWrongIndex() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getTask(3));

        assertEquals("Index " + 3 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    void shouldGetEpicWhenListIsNotEmpty() {
        taskManager.createTask(epic);
        assertEquals(epic, taskManager.getEpic(0));
    }

    @Test
    public void shouldGetEpicWhenListIsEmpty() {
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getEpic(0));

        assertEquals("Index " + 0 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    public void shouldGetEpicWhenWrongIndex() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getEpic(3));

        assertEquals("Index " + 3 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    void shouldGetSubtaskWhenListIsNotEmpty() {
        taskManager.createTask(epic);
        assertEquals(epic, taskManager.getSubtask(0));
    }

    @Test
    public void shouldGetSubtaskWhenListIsEmpty() {
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getSubtask(0));

        assertEquals("Index " + 0 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    public void shouldGetSubtaskWhenWrongIndex() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getSubtask(3));

        assertEquals("Index " + 3 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    void shouldGetEpicIdWhenListIsNotEmpty() {
        taskManager.createTask(epic);
        assertEquals(2, taskManager.getEpicId(0));
    }

    @Test
    public void shouldGetGetEpicIdWhenListIsEmpty() {
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getEpicId(0));

        assertEquals("Index " + 0 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    public void shouldGetGetEpicIdWhenWrongIndex() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.getEpicId(3));

        assertEquals("Index " + 3 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    void shouldGetCurrentWhenListIsNotEmpty() {
        taskManager.createTask(task);
        assertEquals(2, taskManager.getCurrent());
    }

    @Test
    void shouldGetCurrentWhenListIsEmpty() {
        assertEquals(1, taskManager.getCurrent());
    }

    @Test
    void shouldGetTasksWhenListIsNotEmpty() {
        taskManager.createTask(task);
        List<List<Task>> list = List.of(List.of(task));
        assertEquals(list.get(0), taskManager.getTasks());
    }

    @Test
    void shouldGetTasksWhenListIsEmpty() {
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void shouldDeleteAllTasksWhenListIsNotEmpty() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void shouldDeleteAllTasksWhenListIsEmpty() {
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void shouldGetTaskByIdWhenListIsNotEmpty() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        assertEquals(task, taskManager.getTaskById(1));
    }

    @Test
    void shouldGetTaskByIdWhenListIsEmpty() {
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    void shouldGetTaskByIdWhenWrongId() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        assertNull(taskManager.getTaskById(5));
    }

    @Test
    void shouldCreateTaskWhenListIsNotEmpty() {
        taskManager.createTask(task);
        assertFalse(taskManager.getTasks().isEmpty());
    }

    @Test
    void shouldCreateTaskWhenListIsEmpty() {
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void shouldDeleteTaskByIdWhenListIsNotEmpty() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        taskManager.deleteTaskById(2);
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void shouldDeleteTaskByIdWhenListIsEmpty() {
        taskManager.deleteTaskById(2);
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void shouldDeleteTaskByIdWhenWrongId() {
        taskManager.deleteTaskById(101);
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void shouldPrintSubtasksInEpicWhenListIsNotEmpty() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        assertEquals(epic.getSubtasks(), taskManager.printSubtasksInEpic(1));
    }

    @Test
    void shouldPrintSubtasksInEpicWhenListIsEmptyOrIdNotInList() {
        IndexOutOfBoundsException ex = assertThrows(
                IndexOutOfBoundsException.class,
                () -> taskManager.printSubtasksInEpic(1));

        assertEquals("Index " + 1 + " out of bounds for length " + taskManager.getTasks().size(),
                ex.getMessage());
    }

    @Test
    void shouldPrintSubtasksInEpicWhenWrongId() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        assertNull(taskManager.printSubtasksInEpic(2));
    }

    @Test
    void shouldGetHistoryWhenListIsNotEmpty() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        taskManager.getTask(1);
        assertEquals(1, taskManager.getHistory().size());
    }

    @Test
    void shouldGetHistoryWhenListIsEmpty() {
        taskManager.createTask(task);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);
        assertEquals(0, taskManager.getHistory().size());
    }

    @Test
    void shouldRemoveWhenListIsEmpty() {
        assertEquals(taskManager.getHistoryManager().getHistory(),
                taskManager.getTasks());
    }

    @Test
    void shouldRemoveWhenTaskAtFirst() {
        taskManager.createTask(task);
        taskManager.createTask(new Task(
                "1st Task",
                "first",
                2,
                TaskStatus.NEW
        ));
        taskManager.createTask(new Task(
                "1st Task",
                "first",
                3,
                TaskStatus.NEW
        ));
        taskManager.getTask(0);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.deleteTaskById(1);
        assertEquals(2, taskManager.getHistoryManager().getHistory().size());
    }

    @Test
    void shouldRemoveWhenTaskAtMiddle() {
        taskManager.createTask(task);
        taskManager.createTask(new Task(
                "1st Task",
                "first",
                2,
                TaskStatus.NEW
        ));
        taskManager.createTask(new Task(
                "1st Task",
                "first",
                3,
                TaskStatus.NEW
        ));
        taskManager.getTask(0);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.deleteTaskById(2);
        assertEquals(2, taskManager.getHistoryManager().getHistory().size());
    }

    @Test
    void shouldAddWhenDuplication() {
        taskManager.createTask(task);
        taskManager.getTask(0);
        taskManager.getTask(0);
        assertEquals(1, taskManager.getHistoryManager().getHistory().size());
    }
}