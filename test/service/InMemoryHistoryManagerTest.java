package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addTask() {
        //Проверка сохранения истории в HistoryManager
        TaskManager taskManager = Managers.getDefaultNoFile();
        Task task1 = new Task("Task1", "TDescript1", Status.NEW);
        Task task2 = new Task("Task2", "TDescript2", Status.NEW);
        Task task3 = new Task("Task3", "TDescript3", Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTasksById(0);
        taskManager.getTasksById(1);
        taskManager.getTasksById(2);

        //порядок истории из 3 элементов 0, 1, 2
        assertEquals(0, taskManager.getHistory().get(0).getId());
        assertEquals(1, taskManager.getHistory().get(1).getId());
        assertEquals(2, taskManager.getHistory().get(2).getId());

        taskManager.getTasksById(0);
        //посмотрели повторно первый элемент с id=0
        //ожидаемый порядок номеров в истории 1, 2, 0
        assertEquals(1, taskManager.getHistory().get(0).getId());
        assertEquals(2, taskManager.getHistory().get(1).getId());
        assertEquals(0, taskManager.getHistory().get(2).getId());

        taskManager.getTasksById(2);
        //посмотрели повторно второй элемент с id=2
        //ожидаемый порядок номеров в истории 1, 0, 2
        assertEquals(1, taskManager.getHistory().get(0).getId());
        assertEquals(0, taskManager.getHistory().get(1).getId());
        assertEquals(2, taskManager.getHistory().get(2).getId());

        taskManager.getTasksById(2);
        //посмотрели повторно третий элемент с id=2
        //ожидаемый порядок в истории  1, 0, 2
        assertEquals(1, taskManager.getHistory().get(0).getId());
        assertEquals(0, taskManager.getHistory().get(1).getId());
        assertEquals(2, taskManager.getHistory().get(2).getId());


        //проверка размера истории
        assertEquals(3, taskManager.getHistory().size());


    }
}