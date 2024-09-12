package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history;
    private final int startHistoryIndex = 0;
    private int historyIndex = 0;
    private final int initialCapacity = 10;

    public InMemoryHistoryManager() {
        history = new ArrayList<>(initialCapacity);
    }


    @Override
    public <T extends Task> void add (T task) {
        if (historyIndex < 10) {
            history.add(task);
            historyIndex++;
        } else {
            history.remove(startHistoryIndex);
            history.add(task);
        }
    }



    @Override
    public List<Task> getAll() {
        return history;
    }
}
