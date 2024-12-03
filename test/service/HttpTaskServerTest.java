package service;

import com.google.gson.Gson;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    private static final int PORT = 8080;
    private Gson gson = Managers.getGson();
    private TaskManager taskManager;
    private HttpTaskServer httpServer;

    Task task1 = new Task("Task1", "TDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T00:30"), Duration.parse("PT5M"));//0
    Task task2 = new Task("Task2", "TDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T00:20"), Duration.parse("PT5M"));//1
    Task task3 = new Task("Task3", "TDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T00:00"), Duration.parse("PT5M"));//2

    Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T02:30"), Duration.parse("PT5M"));//3
    Epic epic2 = new Epic("Epic2", "EDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T02:20"), Duration.parse("PT5M"));//4
    Epic epic3 = new Epic("Epic3", "EDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T02:10"), Duration.parse("PT5M"));//5

    SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T05:30"), Duration.parse("PT5M"), 3);//6
    SubTask subTask2 = new SubTask("subtask2", "StDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T05:40"), Duration.parse("PT5M"), 4);//7
    SubTask subTask3 = new SubTask("subtask3", "StDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T05:51"), Duration.parse("PT5M"), 5);//8

    @BeforeEach
    void init() throws IOException {
        taskManager = Managers.getDefaultNoFile();
        httpServer = new HttpTaskServer(taskManager);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        httpServer.start();
    }

    @AfterEach
    void tearDown() {
        httpServer.stop();
    }

    @Test
    void getTaskByID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Task FromJson = gson.fromJson(response.body(), Task.class);
        assertEquals(taskManager.getTasksById(FromJson.getId()).getId(), FromJson.getId());
    }

    @Test
    void getSubTaskByID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/6");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        SubTask FromJson = gson.fromJson(response.body(), SubTask.class);
        assertEquals(taskManager.getSubTasksById(FromJson.getId()).getId(), FromJson.getId());
    }

    @Test
    void getEpicByID() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/3");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Epic FromJson = gson.fromJson(response.body(), Epic.class);
        assertEquals(taskManager.getEpicsById(FromJson.getId()).getId(), FromJson.getId());
    }

    @Test
    void postTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/");
        String testBody = "{\n" +
                "\t\"name\": \"Task7\",\n" +
                "\t\"description\": \"TDescript7\",\n" +
                "\t\"status\": \"NEW\",\n" +
                "\t\"startTime\": \"2024-12-30T00:00:00\",\n" +
                "\t\"duration\": \"PT25M\"\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(testBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("Task7", taskManager.getTasksById(9).getName());
    }

    @Test
    void postSubTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/");
        String testBody = "{\n" +
                "\t\"epicId\": 5,\n" +
                "\t  \"name\": \"subtask99\",\n" +
                "\t\t\"description\": \"StDescript1\",\n" +
                "\t\t\"status\": \"NEW\",\n" +
                "\t\t\"startTime\": \"2024-02-01T05:30:00\",\n" +
                "\t\t\"duration\": \"PT5M\"\n" +
                "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(testBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("subtask99", taskManager.getSubTasksById(9).getName());
    }

    @Test
    void postEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/");
        String testBody = "{\n" +
                "\t\t\"listSubTasksId\": [\n" +
                "\t\t\t\n" +
                "\t\t],\n" +
                "\t\t\"name\": \"Epic100\",\n" +
                "\t\t\"description\": \"EDescript1\",\n" +
                "\t\t\"status\": \"NEW\",\n" +
                "\t\t\"startTime\": \"2024-10-11T05:30:00\",\n" +
                "\t\t\"duration\": \"PT5M\"\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(testBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("Epic100", taskManager.getEpicsById(9).getName());
    }

}