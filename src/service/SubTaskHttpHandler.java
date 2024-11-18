package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.util.Optional;

public class SubTaskHttpHandler extends TaskHttpHandler {
    public SubTaskHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (getEndpoint(exchange)) {
                case GET_ALL: {
                    String allTasks = gson.toJson(taskManager.getSubTasks());
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
                    if (taskManager.getSubTasksById(id) == null) {
                        sendText(exchange, "Task c id-" + id + " отсутвует", 404);
                        return;
                    }
                    String task = gson.toJson(taskManager.getSubTasksById(id));
                    sendText(exchange, task, 200);
                    break;
                }

                case ADD: {
                    String jsonTask = getTaskFromJson(exchange);
                    SubTask subTask = gson.fromJson(jsonTask, SubTask.class);
                    try {
                        taskManager.addSubTask(subTask);
                        sendText(exchange, "подзадача добавлена", 200);
                        break;
                    } catch (ManagerSaveException e) {
                        sendText(exchange, "пересечение с существующей подзадачей", 406);
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
                    if (taskManager.getSubTasksById(id) == null) {
                        sendText(exchange, "subTask c id-" + id + " отсутвует", 404);
                        return;
                    }
                    try {
                        SubTask subTask = gson.fromJson(jsonTask, SubTask.class);
                        subTask.setId(id);
                        taskManager.updateSubTask(subTask);
                        sendText(exchange, "подзадача обновлена", 200);
                        break;
                    } catch (ManagerSaveException e) {
                        sendText(exchange, "пересечение с существующей подзадачей", 406);
                        break;
                    }
                }

                case DELETE_ALL: {
                    taskManager.clearSubTasks();
                    sendText(exchange, "подзадачи очищены", 200);
                    break;
                }

                case DELETE_ID: {
                    Optional<Integer> idOpt = getPostId(exchange);
                    if (idOpt.isEmpty()) {
                        sendText(exchange, "Некорректный идентификатор", 400);
                        return;
                    }
                    int id = idOpt.get();
                    if (taskManager.getSubTasksById(id) == null) {
                        sendText(exchange, "subTask c id-" + id + " отсутвует", 404);
                        return;
                    }
                    taskManager.removeSubTasksById(id);
                    sendText(exchange, "subTask c id-" + id + " удален", 200);
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
