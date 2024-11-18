package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

public class Managers {

    public static TaskManager getDefaultNoFile() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new InMemoryHistoryManager(), "java-kanban-data.csv");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalTimeTypeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationTypeAdapter());

        return gsonBuilder.create();
    }


}
