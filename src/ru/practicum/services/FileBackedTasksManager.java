package ru.practicum.services;

import ru.practicum.models.Epic;
import ru.practicum.models.Subtask;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int isEmptyCounter = 0;
            List<String> fileData = new ArrayList<>();
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line.isEmpty()) {
                    isEmptyCounter++;
                    continue;
                }
                fileData.add(line);
            }
            for (int i = 1; i < fileData.size(); i++) {
                if (isEmptyCounter == 1 && i == fileData.size() - 1) {
                    for (Integer id : historyFromString(fileData.get(i))) {
                        fileBackedTasksManager.getTask(id - 1);
                    }
                } else {
                    fileBackedTasksManager.createTaskWhenReading(fileBackedTasksManager.fromString(fileData.get(i)));
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при загрузке файла");
        }
        return fileBackedTasksManager;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    public void createTaskWhenReading(Task task) {
        super.createTask(task);
    }


    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    public void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,epic,start,duration");
            bufferedWriter.newLine();
            if (!getTasks().isEmpty()) {
                for (Task task : getTasks()) {
                    bufferedWriter.write(toString(task));
                    bufferedWriter.newLine();
                }
                if (!getHistory().isEmpty()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(historyToString(getHistoryManager()));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла");
        }
    }

    public String toString(Task task) {
        String[] taskToString = new String[8];
        taskToString[0] = Integer.toString(task.getTaskId());
        taskToString[1] = task.getClass().getSimpleName().toUpperCase();
        taskToString[2] = task.getTaskName();
        taskToString[3] = task.getTaskStatus().toString();
        taskToString[4] = task.getDescription();
        if (task.getClass().getSimpleName().equals("Subtask")) {
            taskToString[5] = Integer.toString(((Subtask) task).getEpicId());
        } else {
            taskToString[5] = " ";
        }
        if (task.getStartTime() == null || task.getTaskDuration() == null) {
            taskToString[6] = " ";
            taskToString[7] = " ";
        } else {
            taskToString[6] = task.getStartTime().toString();
            taskToString[7] = task.getTaskDuration().toString();
        }
        return String.join(",", taskToString);
    }

    public Task fromString(String value) {
        String[] stringToTask = value.split(",");
        switch (stringToTask[1]) {
            case "TASK" -> {
                if (stringToTask[6].isBlank() && stringToTask[7].isBlank()) {
                    return new Task(stringToTask[2],
                            stringToTask[4],
                            Integer.parseInt(stringToTask[0]),
                            TaskStatus.valueOf(stringToTask[3]));
                } else {
                    return new Task(stringToTask[2], stringToTask[4],
                            Integer.parseInt(stringToTask[0]),
                            TaskStatus.valueOf(stringToTask[3]),
                            Duration.parse(stringToTask[7]),
                            LocalDateTime.parse(stringToTask[6]));
                }
            }
            case "EPIC" -> {
                if (stringToTask[6].isBlank() && stringToTask[7].isBlank()) {
                    return new Epic(stringToTask[2],
                            stringToTask[4],
                            Integer.parseInt(stringToTask[0]),
                            TaskStatus.valueOf(stringToTask[3]));
                } else {
                    return new Epic(stringToTask[2], stringToTask[4],
                            Integer.parseInt(stringToTask[0]),
                            TaskStatus.valueOf(stringToTask[3]),
                            Duration.parse(stringToTask[7]),
                            LocalDateTime.parse(stringToTask[6]));
                }
            }
            case "SUBTASK" -> {
                if (stringToTask[6].isBlank() && stringToTask[7].isBlank()) {
                    return new Subtask(stringToTask[2],
                            stringToTask[4],
                            Integer.parseInt(stringToTask[0]),
                            TaskStatus.valueOf(stringToTask[3]),
                            Integer.parseInt(stringToTask[5]));
                } else {
                    return new Subtask(stringToTask[2],
                            stringToTask[4],
                            Integer.parseInt(stringToTask[0]),
                            TaskStatus.valueOf(stringToTask[3]),
                            Duration.parse(stringToTask[7]),
                            LocalDateTime.parse(stringToTask[6]),
                            Integer.parseInt(stringToTask[5]));
                }
            }
            default -> throw new RuntimeException("Не удалось перенести Строку в Таск");
        }
    }

    static String historyToString(HistoryManager manager) {
        List<String> IntToString = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            IntToString.add(Integer.toString(task.getTaskId()));
        }
        return String.join(",", IntToString);
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> stringToList = new ArrayList<>();
        for (String s : value.split(",")) {
            stringToList.add(Integer.parseInt(s));
        }
        return stringToList;
    }

    public void printFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            List<String> list = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            if (!(list.size() == 1)) {
                String[] strings = list.toArray(new String[0]);
                for (int i = 1; i < strings.length; i++) {
                    if (strings[i].isEmpty()) {
                        if (strings.length > i + 1) {
                            System.out.println(historyFromString(strings[i + 1]));
                        }
                        break;
                    } else {
                        System.out.println(fromString(strings[i]).toString());
                    }
                }
            }

        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка вывода информации из файла");
        }
    }

    public static void main(String[] args) {
        System.out.println("CREATING NEW FILE...\n");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("Task.csv"));
        Epic epic;
        Subtask subtask;

        epic = new Epic("1", "1", 1);
        epic.checker();
        fileBackedTasksManager.createTask(epic);

        subtask = new Subtask("2", "2", 2, TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2001, 11, 27, 4, 30), 0);
        ((Epic) fileBackedTasksManager.getEpic(0)).addSubtask(subtask);
        ((Epic) fileBackedTasksManager.getEpic(0)).checkerWithDurationAndDateTime();
        fileBackedTasksManager.createTask(subtask);

        subtask = new Subtask("3", "3", 3, TaskStatus.IN_PROGRESS, Duration.ofMinutes(30), LocalDateTime.of(2001, 12, 27, 4, 30), 0);
        ((Epic) fileBackedTasksManager.getEpic(0)).addSubtask(subtask);
        ((Epic) fileBackedTasksManager.getEpic(0)).checkerWithDurationAndDateTime();
        fileBackedTasksManager.createTask(subtask);

        subtask = new Subtask("4", "4", 4, TaskStatus.DONE, Duration.ofMinutes(30), LocalDateTime.of(2001, 10, 27, 4, 30), 0);
        ((Epic) fileBackedTasksManager.getEpic(0)).addSubtask(subtask);
        ((Epic) fileBackedTasksManager.getEpic(0)).checkerWithDurationAndDateTime();
        fileBackedTasksManager.createTask(subtask);

        System.out.println(fileBackedTasksManager.getTask(0));
        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getTask(2));
        System.out.println(fileBackedTasksManager.getTask(3));

        System.out.println("\nREADING THE CREATED FILE...\n");

        fileBackedTasksManager.printFile();

        System.out.println("\nREADING THE CREATED FILE AND WRITING NEW FILE...\n");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(new File("Task.csv"));

        fileBackedTasksManager1.printFile();
    }
}

