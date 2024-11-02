package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    @Test
    void addTaskTest() {
        //Проверка добавления и поиска задачи по ID
        InMemoryTaskManager tm = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task1 = new Task("Task1", "TDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T00:30"), Duration.parse("PT5M"));//0
        tm.addTask(task1);
        assertEquals(task1.getName(), tm.getTasksById(task1.getId()).getName());
    }

    @Test
    void addEpicTest() {
        //Проверка добавления и поиска епика по ID
        InMemoryTaskManager tm = new InMemoryTaskManager(new InMemoryHistoryManager());
        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T02:30"), Duration.parse("PT5M"));//3
        tm.addTask(epic1);
        assertEquals(epic1.getName(), tm.getTasksById(epic1.getId()).getName());
    }

    @Test
    void addSubTask() {
        //Проверка добавления и поиска сабТаска по ID
        InMemoryTaskManager tm = new InMemoryTaskManager(new InMemoryHistoryManager());
        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T05:30"), Duration.parse("PT5M"), 3);//6
        tm.addTask(subTask1);
        assertEquals(subTask1.getName(), tm.getTasksById(subTask1.getId()).getName());
    }


}