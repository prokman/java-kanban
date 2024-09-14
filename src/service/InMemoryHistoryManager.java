package service;

import model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history;
    private final int startHistoryIndex = 0;
    private int historyIndex = 0;
    private static final int historyCapacity = 10;

    public InMemoryHistoryManager() {
        history = new LinkedList<>();
    }


    @Override
    public <T extends Task> void add(T task) {
        if (task != null) {
            if (historyIndex < historyCapacity) {
                history.add(task);
                historyIndex++;
            } else {
                history.remove(startHistoryIndex);
                history.add(task);
            }
        }

    }

    @Override
    public List<Task> getAll() {
        return new LinkedList<>(history);
    }
}
