package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private Gson gson;
    private TaskManager taskManager;
    private HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultNoFile());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        //public HttpTaskServer() throws IOException {
        this.taskManager = taskManager;
        //Managers.getDefaultNoFile();
        gson = Managers.getGson();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHttpHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubTaskHttpHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicHttpHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHttpHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedTasksHttpHandler(taskManager, gson));
    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("HTTP-сервер остановлен на " + PORT + " порту!");
    }

    public static void main(String[] args) throws IOException {

        HttpTaskServer server = new HttpTaskServer();

        Task task1 = new Task("Task1", "TDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T00:30"), Duration.parse("PT5M"));//0
        Task task2 = new Task("Task2", "TDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T00:20"), Duration.parse("PT5M"));//1
        Task task3 = new Task("Task3", "TDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T00:00"), Duration.parse("PT5M"));//2

        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T02:30"), Duration.parse("PT5M"));//3
        Epic epic2 = new Epic("Epic2", "EDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T02:20"), Duration.parse("PT5M"));//4
        Epic epic3 = new Epic("Epic3", "EDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T02:10"), Duration.parse("PT5M"));//5

        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T05:30"), Duration.parse("PT5M"), 3);//6
        SubTask subTask2 = new SubTask("subtask2", "StDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T05:40"), Duration.parse("PT5M"), 4);//7
        SubTask subTask3 = new SubTask("subtask3", "StDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T05:51"), Duration.parse("PT5M"), 5);//8


        server.taskManager.addTask(task1);
        server.taskManager.addTask(task2);
        server.taskManager.addTask(task3);

        server.taskManager.addEpic(epic1);
        server.taskManager.addEpic(epic2);
        server.taskManager.addEpic(epic3);

        server.taskManager.addSubTask(subTask1);
        server.taskManager.addSubTask(subTask2);
        server.taskManager.addSubTask(subTask3);

        server.start();
    }
}






