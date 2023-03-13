package ru.practicum.services.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.practicum.models.Task;
import ru.practicum.services.FileBackedTasksManager;
import ru.practicum.utils.Manager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient client;
    private final Gson gson;

    public HttpTaskManager(String url) {
        super(url);
        try {
            client = new KVTaskClient(url);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        gson = Manager.getDefaultGson();
    }

    @Override
    public void save() {
        String task = gson.toJson(getTasks());

        String history = gson.toJson(getHistory().stream().map(Task::getTaskId).collect(Collectors.toList()));

        client.put("tasks", task);
        client.put("history", history);

    }

    public void loadFromKVServer() {
        String taskJson = client.load("tasks");
        String history = client.load("history");

        List<Task> tasks = gson.fromJson((taskJson), new TypeToken<ArrayList<Task>>() {}.getType());
        if (tasks != null) {
            tasks.forEach(this::createTask);
        }

        List<Integer> historyMemory = gson.fromJson((history), new TypeToken<ArrayList<Integer>>() {}.getType());
        historyMemory.forEach(super::getTask);
    }
}