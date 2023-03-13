package ru.practicum.services.http;

import ru.practicum.services.ManagerLoadException;
import ru.practicum.services.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class KVTaskClient {
    private final String API_TOKEN;
    private final String URL;

    public KVTaskClient(String URL) throws IOException, InterruptedException {
        this.URL = URL;
        URI uri = URI.create(this.URL + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        API_TOKEN = response.body();
    }

    public void put(String key, String json) {
        URI uri = URI.create(URL + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Ошибка сохранения данных, код состояния " + response.statusCode());
            }
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String load(String key) {
        URI uri = URI.create(URL + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerLoadException("Ошибка загрузки, код ошибки - " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }

    }
}