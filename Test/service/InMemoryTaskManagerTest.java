package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    @Test
    void addTaskTest() {
        //Проверка добавления и поиска задачи по ID
        InMemoryTaskManager tm = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task1 = new Task("Task1", "TDescript1", Status.NEW);
        tm.addTask(task1);
        assertEquals(task1.getName(), tm.getTasksById(task1.getId()).getName());
    }

    @Test
    void addEpicTest() {
        //Проверка добавления и поиска епика по ID
        InMemoryTaskManager tm = new InMemoryTaskManager(new InMemoryHistoryManager());
        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW);
        tm.addTask(epic1);
        assertEquals(epic1.getName(), tm.getTasksById(epic1.getId()).getName());
    }

    @Test
    void addSubTask() {
        //Проверка добавления и поиска сабТаска по ID
        InMemoryTaskManager tm = new InMemoryTaskManager(new InMemoryHistoryManager());
        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, 1);
        tm.addTask(subTask1);
        assertEquals(subTask1.getName(), tm.getTasksById(subTask1.getId()).getName());
    }


}