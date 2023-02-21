package ru.practicum.services;

import ru.practicum.models.Epic;
import ru.practicum.models.Subtask;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
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
                    fileBackedTasksManager.updateTask(fileBackedTasksManager.fromString(fileData.get(i)));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка вывода");
        }
        return fileBackedTasksManager;
    }

    @Override
    public void createTask(Task task) throws ManagerSaveException {
        super.createTask(task);
        save();
    }

    @Override
    public Task getTask(int id) throws ManagerSaveException {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public void deleteAllTasks() throws ManagerSaveException {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteTaskById() throws ManagerSaveException {
        super.deleteTaskById();
        save();
    }

    private void save() throws ManagerSaveException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,epic");
            bufferedWriter.newLine();
            if (!getTasks().isEmpty()) {
                for (Task task : getTasks()) {
                    bufferedWriter.write(toString(task));
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
                bufferedWriter.write(historyToString(getHistoryManager()));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка ввода");
        }
    }

    public String toString(Task task) {
        String[] taskToString = new String[6];
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
        return String.join(",", taskToString);
    }

    public Task fromString(String value) {
        String[] stringToTask = value.split(",");
        if (stringToTask[1].equals(TaskName.TASK.toString())) {
            return new Task(stringToTask[2], stringToTask[4], Integer.parseInt(stringToTask[0]),
                    TaskStatus.valueOf(stringToTask[3]));
        } else if (stringToTask[1].equals(TaskName.SUBTASK.toString())) {
            return new Subtask(stringToTask[2], stringToTask[4], Integer.parseInt(stringToTask[0]),
                    TaskStatus.valueOf(stringToTask[3]), Integer.parseInt(stringToTask[5]));
        } else {
            return new Epic(stringToTask[2], stringToTask[4], Integer.parseInt(stringToTask[0]),
                    TaskStatus.valueOf(stringToTask[3]));
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

    public void printFile() throws ManagerSaveException {
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
            throw new ManagerSaveException("Ошибка вывода");
        }
    }

    public static void main(String[] args) throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("Task.csv"));
        Subtask subtask;

        fileBackedTasksManager.createTask(new Task("Помыть посуду", "Взять губку",
                fileBackedTasksManager.getCurrent(), TaskStatus.NEW)); // 1

        fileBackedTasksManager.createTask(new Task("Постирать вещи", "Взять губку",
                fileBackedTasksManager.getCurrent(), TaskStatus.IN_PROGRESS)); // 2


        fileBackedTasksManager.createTask(new Epic("Помыть полы", "Взять губку",
                fileBackedTasksManager.getCurrent(), TaskStatus.NEW)); // 3

        fileBackedTasksManager.createTask(subtask = new Subtask("Купить швабру", "Взять губку",
                fileBackedTasksManager.getCurrent(), TaskStatus.DONE, 2)); // 4
        ((Epic) fileBackedTasksManager.getEpic(2)).addSubtask(subtask);
        ((Epic) fileBackedTasksManager.getEpic(2)).checker();

        fileBackedTasksManager.createTask(subtask = new Subtask("Купить таз", "Взять губку",
                fileBackedTasksManager.getCurrent(), TaskStatus.DONE, 2)); // 5
        ((Epic) fileBackedTasksManager.getEpic(2)).addSubtask(subtask);
        ((Epic) fileBackedTasksManager.getEpic(2)).checker();

        fileBackedTasksManager.createTask(subtask = new Subtask("Порезать колбасу",
                "Взять губку", fileBackedTasksManager.getCurrent(), TaskStatus.NEW, 2)); // 6
        ((Epic) fileBackedTasksManager.getEpic(2)).addSubtask(subtask);
        ((Epic) fileBackedTasksManager.getEpic(2)).checker();

        fileBackedTasksManager.createTask(new Epic("Приготовить бутерброд", "Взять губку",
                fileBackedTasksManager.getCurrent(), TaskStatus.DONE)); // 7
        ((Epic) fileBackedTasksManager.getEpic(6)).checker();

        System.out.println(fileBackedTasksManager.getTask(0));
        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getTask(2));
        System.out.println(fileBackedTasksManager.getTask(3));
        System.out.println(fileBackedTasksManager.getTask(4));
        System.out.println(fileBackedTasksManager.getTask(5));
        System.out.println(fileBackedTasksManager.getTask(6));
        System.out.println(fileBackedTasksManager.getTask(6));
        System.out.println(fileBackedTasksManager.getTask(5));
        System.out.println(fileBackedTasksManager.getTask(4));
        System.out.println(fileBackedTasksManager.getTask(3));

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        fileBackedTasksManager.printFile();

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(new File("Task.csv"));

        fileBackedTasksManager1.printFile();
    }
}

