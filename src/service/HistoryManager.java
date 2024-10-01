package service;

import model.Task;

import java.util.List;

public interface HistoryManager {

    <T extends Task> void add(T task, Integer id);


    void removeNode(Integer id);


    List<Task> getAll();

}
