package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TaskHttpHandler implements HttpHandler {
    protected TaskManager taskManager;
    protected Gson gson;

    public TaskHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    enum Endpoint {
        GET_ALL, GET_BY_ID, ADD, UPDATE, DELETE_ALL, DELETE_ID, UNKNOWN
    }

    protected void sendText(HttpExchange exchange, String text, int responseCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responseCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected Endpoint getEndpoint(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        String[] splitPath = path.split("/");
        int size = splitPath.length;
        if (method.equals("GET") && size == 2) {
            return Endpoint.GET_ALL;
        } else if (method.equals("GET") && size > 2) {
            return Endpoint.GET_BY_ID;
        } else if (method.equals("POST") && size == 2) {
            return Endpoint.ADD;
        } else if (method.equals("POST") && size > 2) {
            return Endpoint.UPDATE;
        } else if (method.equals("DELETE") && size == 2) {
            return Endpoint.DELETE_ALL;
        } else if (method.equals("DELETE") && size > 2) {
            return Endpoint.DELETE_ID;
        } else return Endpoint.UNKNOWN;
    }

    protected Optional<Integer> getPostId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    protected String getTaskFromJson(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (getEndpoint(exchange)) {
                case GET_ALL: {
                    String allTasks = gson.toJson(taskManager.getTasks());
                    sendText(exchange, allTasks, 200);
                    break;

                }

                case GET_BY_ID: {
                    Optional<Integer> idOpt = getPostId(exchange);
                    if (idOpt.isEmpty()) {
                        sendText(exchange, "Некорректный идентификатор", 400);
                        return;
                    }
                    int id = idOpt.get();
                    if (taskManager.getTasksById(id) == null) {
                        sendText(exchange, "Task c id-" + id + " отсутвует", 404);
                        return;
                    }
                    String task = gson.toJson(taskManager.getTasksById(id));
                    sendText(exchange, task, 200);
                    break;
                }

                case ADD: {
                    String jsonTask = getTaskFromJson(exchange);
                    Task task = gson.fromJson(jsonTask, Task.class);
                    try {
                        taskManager.addTask(task);
                        sendText(exchange, "Задача добавлена", 200);
                        break;
                    } catch (ManagerSaveException e) {
                        sendText(exchange, "пересечение с существующей задачей", 406);
                        break;
                    }
                }

                case UPDATE: {
                    String jsonTask = getTaskFromJson(exchange);
                    Optional<Integer> idOpt = getPostId(exchange);
                    if (idOpt.isEmpty()) {
                        sendText(exchange, "Некорректный идентификатор", 400);
                        return;
                    }
                    int id = idOpt.get();
                    if (taskManager.getTasksById(id) == null) {
                        sendText(exchange, "Task c id-" + id + " отсутвует", 404);
                        return;
                    }
                    try {
                        Task task = gson.fromJson(jsonTask, Task.class);
                        task.setId(id);
                        taskManager.updateTask(task);
                        sendText(exchange, "Задача обновлена", 200);
                        break;
                    } catch (ManagerSaveException e) {
                        sendText(exchange, "пересечение с существующей задачей", 406);
                        break;
                    }
                }

                case DELETE_ALL: {
                    taskManager.clearTasks();
                    sendText(exchange, "Задачи очищены", 200);
                    break;
                }

                case DELETE_ID: {
                    Optional<Integer> idOpt = getPostId(exchange);
                    if (idOpt.isEmpty()) {
                        sendText(exchange, "Некорректный идентификатор", 400);
                        return;
                    }
                    int id = idOpt.get();
                    if (taskManager.getTasksById(id) == null) {
                        sendText(exchange, "Task c id-" + id + " отсутвует", 404);
                        return;
                    }
                    taskManager.removeTasksById(id);
                    sendText(exchange, "Task c id-" + id + " удален", 200);
                    break;
                }

                default: {
                    sendText(exchange, "некорректный URL", 404);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

}
