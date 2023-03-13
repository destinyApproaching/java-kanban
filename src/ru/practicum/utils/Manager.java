package ru.practicum.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.practicum.services.*;
import ru.practicum.services.http.Adapters.LocalDateTimeAdapter;
import ru.practicum.services.http.HttpTaskManager;
import ru.practicum.services.http.KVServer;

import java.io.File;
import java.time.LocalDateTime;

public class Manager {
    public static TaskManager getDefaultTaskManager() {
    return FileBackedTasksManager.loadFromFile(new File("Task.csv"));
}
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getDefaultGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    public static HttpTaskManager getDefaultHttp() {
        return new HttpTaskManager("http://localhost:" + KVServer.PORT);
    }
}