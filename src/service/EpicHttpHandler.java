package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Epic;

import java.io.IOException;
import java.util.Optional;

public class EpicHttpHandler extends TaskHttpHandler {

    public EpicHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (getEndpoint(exchange)) {
                case GET_ALL: {
                    String allTasks = gson.toJson(taskManager.getEpics());
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
                    if (taskManager.getEpicsById(id) == null) {
                        sendText(exchange, "Epic c id-" + id + " отсутвует", 404);
                        return;
                    }
                    String task = gson.toJson(taskManager.getEpicsById(id));
                    sendText(exchange, task, 200);
                    break;
                }

                case ADD: {
                    String jsonTask = getTaskFromJson(exchange);
                    Epic epic = gson.fromJson(jsonTask, Epic.class);
                    try {
                        taskManager.addEpic(epic);
                        sendText(exchange, "Эпик добавлен", 200);
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
                    if (taskManager.getEpicsById(id) == null) {
                        sendText(exchange, "Task c id-" + id + " отсутвует", 404);
                        return;
                    }
                    try {
                        Epic epic = gson.fromJson(jsonTask, Epic.class);
                        epic.setId(id);
                        taskManager.updateTask(epic);
                        sendText(exchange, "Задача обновлена", 200);
                        break;
                    } catch (ManagerSaveException e) {
                        sendText(exchange, "пересечение с существующей задачей", 406);
                        break;
                    }
                }

                case DELETE_ALL: {
                    taskManager.clearEpics();
                    sendText(exchange, "Эпики очищены", 200);
                    break;
                }

                case DELETE_ID: {
                    Optional<Integer> idOpt = getPostId(exchange);
                    if (idOpt.isEmpty()) {
                        sendText(exchange, "Некорректный идентификатор", 400);
                        return;
                    }
                    int id = idOpt.get();
                    if (taskManager.getEpicsById(id) == null) {
                        sendText(exchange, "Epic c id-" + id + " отсутвует", 404);
                        return;
                    }
                    taskManager.removeEpicsById(id);
                    sendText(exchange, "Epic c id-" + id + " удален", 200);
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
