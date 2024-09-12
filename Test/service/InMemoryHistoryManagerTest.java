package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addTask() {
        //Проверка сохранения истории в HistoryManager
        TaskManager taskManager = Managers.getDefault();
        Task task1= new Task("Task1","TDescript1", Status.NEW);
        Task task2= new Task("Task2","TDescript2", Status.NEW);
        Task task3= new Task("Task3","TDescript3", Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTasksById(0);//станет 2
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);//id 2 переедет на 0 адрес истории
        taskManager.getTasksById(0);
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);
        taskManager.getTasksById(0);
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);
        taskManager.getTasksById(0);//10
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);


        assertEquals(2,taskManager.getHistory().get(0).getId());


    }
}