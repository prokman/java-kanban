package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class PrioritizedTasksHttpHandler extends TaskHttpHandler {
    public PrioritizedTasksHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    protected Endpoint getEndpoint(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String[] splitPath = path.split("/");
        int size = splitPath.length;
        if (method.equals("GET") && size == 2) {
            return Endpoint.GET_ALL;
        } else {
            return Endpoint.UNKNOWN;
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (getEndpoint(exchange)) {
                case GET_ALL: {
                    String allTasks = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(exchange, allTasks, 200);
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
