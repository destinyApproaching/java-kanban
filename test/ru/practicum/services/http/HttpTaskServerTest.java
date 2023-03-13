package ru.practicum.services.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.jupiter.api.*;
import ru.practicum.models.Task;
import ru.practicum.models.TaskStatus;
import ru.practicum.services.FileBackedTasksManager;
import ru.practicum.services.TaskManager;
import ru.practicum.utils.Manager;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private static KVServer kvServer;
    private static Gson gson;
    private static HttpTaskServer httpTaskServer;
    private TaskManager taskManager;
    Task task1;
    Task task2;

    private void createTasks() {
        task1 = new Task("1",
                "1",
                1,
                TaskStatus.IN_PROGRESS,
                40L,
                LocalDateTime.of(2020, 12, 21, 12, 30));
        task2 = new Task("2",
                "2",
                2,
                TaskStatus.IN_PROGRESS,
                40L,
                LocalDateTime.of(2007, 12, 21, 12, 30));
        taskManager.createTask(task1);
        taskManager.createTask(task2);
    }

    @BeforeAll
    static void start() throws IOException {
        gson = Manager.getDefaultGson();
        kvServer = new KVServer();
        kvServer.start();
    }

    @BeforeEach
    void setUp() throws IOException {
        taskManager = new FileBackedTasksManager("Task.csv");
        createTasks();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
    }

    @AfterEach
    public void tearDown() {
        httpTaskServer.stop();
    }

    @AfterAll
    static void stop() {
        kvServer.stop();
    }

    @Test
    void postTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/");

        String taskJson1 = gson.toJson(task1);
        String taskJson2 = gson.toJson(task2);


        HttpRequest.BodyPublisher bodyJson1 = HttpRequest.BodyPublishers.ofString(taskJson1);
        HttpRequest.BodyPublisher bodyJson2 = HttpRequest.BodyPublishers.ofString(taskJson2);


        HttpRequest request1 = HttpRequest.newBuilder().POST(bodyJson1).uri(uri).build();
        HttpRequest request2 = HttpRequest.newBuilder().POST(bodyJson2).uri(uri).build();


        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());


        assertEquals(200, response1.statusCode());
        assertEquals(200, response2.statusCode());

    }

    @Test
    void getTaskResponseTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertNotNull(response.body());
    }

    @Test
    void deleteTaskByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/" + "id=1");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void deleteTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getEmptyHistoryTest() throws IOException, InterruptedException {
        taskManager.getTask(1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertNotNull(gson.fromJson(response.body(), new TypeToken<List<Task>>(){}.getType()));
        assertEquals(response.body(), "[]");
    }

    @Test
    void getHistoryTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/history/");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("[]" ,response.body());

    }

    @Test
    void prioritizedTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(response.body(), gson.toJson(taskManager.getPrioritizedTasks()));

    }

}