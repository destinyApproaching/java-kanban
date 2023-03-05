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
    private final Set<Task> prioritizedTasks = new TreeSet<>((o1, o2) -> {
        if (o1.getStartTime() == null && o2.getStartTime() == null)
            return 1;
        if (o1.getStartTime() == null && o2.getStartTime() != null)
            return 1;
        if (o1.getStartTime() != null && o2.getStartTime() == null)
            return -1;
        if (o1.getStartTime().equals(o2.getStartTime())) {
            if (o1.getClass().getSimpleName().equals("Subtask")
                    && ((Subtask) o1).getEpicId() == o2.getTaskId() - 1) {
                return 1;
            }
            if (o2.getClass().getSimpleName().equals("Subtask")
                    && ((Subtask) o2).getEpicId() == o1.getTaskId() - 1) {
                return 1;
            }
            return 0;
        }
        return o1.getStartTime().compareTo(o2.getStartTime());
    });

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
        prioritizedTasks.clear();
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
        if (isValid(task)) {
            prioritizedTasks.add(task);
            tasks.clear();
            tasks.addAll(prioritizedTasks);
            getNextCurrent();
        } else {
            System.out.println("ТАСК НЕ ВАЛИДЕН!");
        }
    }

    @Override
    public void updateTask(Task newTask, int id) {
        for (Task task : tasks) {
            if (task.getTaskId() == id && isValid(newTask)) {
                deleteTaskById(id);
                createTask(newTask);
                System.out.println("Таск был успешно обновлён.");
                break;
            }
        }
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
        deleteTasks.forEach(prioritizedTasks::remove);
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
        return prioritizedTasks;
    }

    public boolean isValid(Task task) {
        if (task == null) {
            return true;
        }
        for (Task prioritizedTask : prioritizedTasks) {
            if (prioritizedTask.getStartTime() == null || task.getStartTime() == null) {
                return true;
            }
            if (prioritizedTask.getStartTime().equals(task.getStartTime())) {
                if (prioritizedTask.getClass().getSimpleName().equals("Subtask")
                        && ((Subtask) prioritizedTask).getEpicId() == task.getTaskId() - 1) {
                    return true;
                }
                if (task.getClass().getSimpleName().equals("Subtask")
                        && ((Subtask) task).getEpicId() == prioritizedTask.getTaskId() - 1) {
                    return true;
                }
                return false;
            }
            if (task.getStartTime().isAfter(prioritizedTask.getStartTime())
                    && task.getStartTime().isBefore(prioritizedTask.getEndTime())
                    || task.getEndTime().isAfter(prioritizedTask.getStartTime())
                    && task.getEndTime().isBefore(prioritizedTask.getEndTime())
                    || prioritizedTask.getStartTime().isAfter(task.getStartTime())
                    && prioritizedTask.getStartTime().isBefore(task.getEndTime())
                    || prioritizedTask.getEndTime().isAfter(task.getStartTime())
                    && prioritizedTask.getEndTime().isBefore(task.getEndTime())) {
                return true;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic;
        Subtask subtask;

        epic = new Epic("1",
                "1",
                1);
        epic.checker();
        inMemoryTaskManager.createTask(epic);

        subtask = new Subtask("2",
                "2",
                2,
                TaskStatus.NEW,
                Duration.ofMinutes(30),
                LocalDateTime.of(2000, 11, 27, 4, 30), 0);
        ((Epic) inMemoryTaskManager.getEpic(0)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(0)).checkerWithDurationAndDateTime();
        inMemoryTaskManager.createTask(subtask);

        subtask = new Subtask("3",
                "3",
                3,
                TaskStatus.IN_PROGRESS,
                Duration.ofMinutes(30),
                LocalDateTime.of(2008, 12, 27, 4, 30), 0);
        ((Epic) inMemoryTaskManager.getEpic(0)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(0)).checkerWithDurationAndDateTime();
        inMemoryTaskManager.createTask(subtask);

        subtask = new Subtask("4",
                "4",
                4,
                TaskStatus.DONE,
                Duration.ofMinutes(30),
                LocalDateTime.of(2004, 10, 27, 4, 30), 0);
        ((Epic) inMemoryTaskManager.getEpic(0)).addSubtask(subtask);
        ((Epic) inMemoryTaskManager.getEpic(0)).checkerWithDurationAndDateTime();
        inMemoryTaskManager.createTask(subtask);

        inMemoryTaskManager.createTask(new Task("5",
                "5",
                5,
                TaskStatus.DONE, Duration.ofMinutes(30),
                LocalDateTime.of(2009, 10, 27, 4, 30)));

        inMemoryTaskManager.createTask(new Task("6",
                "6",
                6,
                TaskStatus.DONE));

        inMemoryTaskManager.createTask(new Task("7",
                "7",
                7,
                TaskStatus.DONE,
                Duration.ofMinutes(30),
                LocalDateTime.of(1996, 10, 27, 4, 30)));

        inMemoryTaskManager.createTask(new Task("8",
                "8",
                8,
                TaskStatus.DONE) );

        inMemoryTaskManager.createTask(new Task("9",
                "9",
                9,
                TaskStatus.DONE,
                Duration.ofMinutes(30),
                LocalDateTime.of(2022, 10, 27, 4, 30)));

        inMemoryTaskManager.createTask(new Task("10",
                "10",
                10,
                TaskStatus.DONE,
                Duration.ofMinutes(30),
                LocalDateTime.of(2022, 10, 27, 4, 30)));

        for (Task task : inMemoryTaskManager.getTasks()) {
            System.out.println(task.getTaskId() + " " + task.getStartTime());
        }

        inMemoryTaskManager.updateTask(new Task("11",
                "11",
                1,
                TaskStatus.IN_PROGRESS,
                Duration.ofMinutes(90),
                LocalDateTime.of(2015, 10, 27, 4, 30)), 1);

        for (Task task : inMemoryTaskManager.getTasks()) {
            System.out.println(task.getTaskId() + " " + task.getStartTime());
        }

        inMemoryTaskManager.updateTask(new Task("12",
                "12",
                1,
                TaskStatus.IN_PROGRESS,
                Duration.ofMinutes(90),
                LocalDateTime.of(1996, 10, 27, 4, 30)), 1);
    }
}
