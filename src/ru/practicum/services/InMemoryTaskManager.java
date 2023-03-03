package ru.practicum.services;

import ru.practicum.models.*;
import ru.practicum.utils.Manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int current = 1;
    private final List<Task> tasks = new ArrayList<>();
    private final HistoryManager inMemoryHistoryManager =
            Manager.getDefaultHistory();
    private final Set<Task> toSort = new TreeSet<>();

    @Override
    public int getCurrent() {
        return current;
    }

    private void getNextCurrent() {
        this.current++;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public int getEpicId(int id) {
        return tasks.get(id).getTaskId();
    }

    @Override
    public Task getEpic(int id) {
        return tasks.get(id);
    }

    @Override
    public Task getTask(int id) {
        inMemoryHistoryManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task getSubtask(int id) {
        return tasks.get(id);
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void deleteAllTasks() {
        for (int i = 1; i <= getCurrent() ; i++) {
            inMemoryHistoryManager.remove(i);
        }
        tasks.clear();
        setCurrent(1);
        System.out.println("Удаление прошло успешно.");
    }

    @Override
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getTaskId() == id) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void createTask(Task task) {
        toSort.add(task);
        tasks.clear();
        tasks.addAll(toSort);
        getNextCurrent();
    }

    @Override
    public void deleteTaskById(int id) {
        List<Task> deleteTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTaskId() == id) {
                deleteTasks.add(task);
                if (task.getClass().getSimpleName().equals("Epic")) {
                    deleteTasks.addAll(((Epic) task).getSubtasks());
                    break;
                }
                break;
            }
        }
        tasks.removeAll(deleteTasks);
        for (Task deleteTask : deleteTasks) {
            inMemoryHistoryManager.remove(deleteTask.getTaskId());
        }
    }

    @Override
    public List<Subtask> printSubtasksInEpic(int id) {
        if (tasks.get(id).getClass() == Epic.class) {
            return ((Epic) tasks.get(id)).getSubtasks();
        } else return null;
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return inMemoryHistoryManager;
    }

    public Set<Task> getPrioritizedTasks() {
        return toSort;
    }

    public boolean isValid() {
        for (int i = 0; i < tasks.size() - 1; i++) {
            if (tasks.get(i).getStartTime() == null || tasks.get(i + 1).getStartTime() == null) {
                return true;
            }
            if (tasks.get(i).getStartTime().equals(tasks.get(i + 1).getStartTime())) {
                if (tasks.get(i).getClass().getSimpleName().equals("Subtask")
                        && ((Subtask) tasks.get(i)).getEpicId() == tasks.get(i + 1).getTaskId() - 1) {
                    continue;
                }
                if (tasks.get(i + 1).getClass().getSimpleName().equals("Subtask")
                        && ((Subtask) tasks.get(i + 1)).getEpicId() == tasks.get(i).getTaskId() - 1) {
                    continue;
                }
                return false;
            }
            if (tasks.get(i).getEndTime().isAfter(tasks.get(i + 1).getStartTime())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic;
        Subtask subtask;

        epic = new Epic("1", "1", 1);
        epic.checker();
        inMemoryTaskManager.createTask(epic);

        subtask = new Subtask("2", "2", 2, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2000, 11, 27, 4, 30), 0);
        ((Epic) inMemoryTaskManager.getEpic(0)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(0)).checkerWithDurationAndDateTime();
        inMemoryTaskManager.createTask(subtask);

        subtask = new Subtask("3", "3", 3, TaskStatus.IN_PROGRESS, Duration.ofMinutes(30), LocalDateTime.of(2008, 12, 27, 4, 30), 0);
        ((Epic) inMemoryTaskManager.getEpic(0)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(0)).checkerWithDurationAndDateTime();
        inMemoryTaskManager.createTask(subtask);

        subtask = new Subtask("4", "4", 4, TaskStatus.DONE, Duration.ofMinutes(30), LocalDateTime.of(2004, 10, 27, 4, 30), 0);
        ((Epic) inMemoryTaskManager.getEpic(0)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(0)).checkerWithDurationAndDateTime();
        inMemoryTaskManager.createTask(subtask);

        inMemoryTaskManager.createTask(new Task("5", "5", 5, TaskStatus.DONE, Duration.ofMinutes(30), LocalDateTime.of(2009, 10, 27, 4, 30)));
        inMemoryTaskManager.createTask(new Task("6", "6", 6, TaskStatus.DONE));
        inMemoryTaskManager.createTask(new Task("7", "7", 7, TaskStatus.DONE, Duration.ofMinutes(30), LocalDateTime.of(1996, 10, 27, 4, 30)));
        inMemoryTaskManager.createTask(new Task("8", "8", 8, TaskStatus.DONE));
        inMemoryTaskManager.createTask(new Task("9", "9", 9, TaskStatus.DONE, Duration.ofMinutes(30), LocalDateTime.of(2022, 10, 27, 4, 30)));
        inMemoryTaskManager.createTask(new Task("10", "10", 10, TaskStatus.DONE, Duration.ofMinutes(30), LocalDateTime.of(2022, 10, 27, 4, 30)));

        System.out.println(inMemoryTaskManager.getPrioritizedTasks());
        System.out.println("Список валидный: " + inMemoryTaskManager.isValid());
    }
}
