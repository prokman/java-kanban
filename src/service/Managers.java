package service;

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


}
