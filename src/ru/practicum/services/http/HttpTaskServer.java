package ru.practicum.services.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;
import ru.practicum.services.TaskManager;
import ru.practicum.utils.Manager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;

    private final Gson gson;
    private final TaskManager taskManager;
    private final HttpServer server;

    public HttpTaskServer() throws IOException {
        taskManager = Manager.getDefaultTaskManager();
        gson = Manager.getDefaultGson();
        server = HttpServer.create();
        server.bind(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks/", this::handleTasks);
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        httpTaskServer.taskManager.createTask(new Task("1", "1", 1, TaskStatus.DONE, 55L, LocalDateTime.of(2020, 11, 20, 11, 40)));
        httpTaskServer.taskManager.createTask(new Task("2", "2", 2, TaskStatus.DONE, 55L, LocalDateTime.of(2021, 11, 20, 11, 40)));
        httpTaskServer.taskManager.createTask(new Task("3", "3", 3, TaskStatus.DONE, 55L, LocalDateTime.of(2017, 11, 20, 11, 40)));
        httpTaskServer.taskManager.createTask(new Task("4", "4", 4, TaskStatus.DONE, 55L, LocalDateTime.of(2002, 11, 20, 11, 40)));
        httpTaskServer.taskManager.getTask(2);
    }

    private void handleTasks(HttpExchange httpExchange) {
        try (httpExchange) {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        String response = gson.toJson(taskManager.getTasks());
                        sendText(httpExchange, response);
                        break;
                    }

                    if (Pattern.matches("^/tasks/$", path)) {
                        String response = gson.toJson(taskManager.getPrioritizedTasks());
                        sendText(httpExchange, response);
                        break;
                    }

                    if (Pattern.matches("^/tasks/history/$", path)) {
                        String response = gson.toJson(taskManager.getHistory());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/task/id=\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный id = " + pathId);
                            httpExchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    }
                    if (Pattern.matches("^/tasks/subtask/epic/id=\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/subtask/epic/id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getEpicId(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный id = " + pathId);
                            httpExchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    }
                }
                case "POST": {
                    String request = readText(httpExchange);
                    try {
                        Task task = gson.fromJson(request, Task.class);
                        int id = task.getTaskId();
                        if (taskManager.getTask(id) != null) {
                            taskManager.updateTask(task, id);
                            httpExchange.sendResponseHeaders(200, 0);
                            System.out.println("Успешное обновление задачи по id " + id);
                        } else {
                            taskManager.createTask(task);
                            httpExchange.sendResponseHeaders(200, 0);
                            int taskId = task.getTaskId();
                            System.out.println("Задача успешно добавлена по id" + taskId);
                        }
                    } catch (JsonSyntaxException ex) {
                        httpExchange.sendResponseHeaders(400, 0);
                        System.out.println("Введен неправильный формат запроса");
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        taskManager.deleteAllTasks();
                        System.out.println("Все задачи удалены");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/task/id=\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            taskManager.deleteTaskById(id);
                            System.out.println("Удалили задачу id = " + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен некорректный id = " + pathId);
                            httpExchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    }
                }
                default: {
                    System.out.println("Ждём GET, POST или DELETE запрос, а получили - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    public String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    public void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
