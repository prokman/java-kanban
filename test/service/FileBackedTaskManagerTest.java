package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    @Test
    void addTask() {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Task1", "TDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T00:30"), Duration.parse("PT5M"));//0
        taskManager.addTask(task1);
        assertEquals(task1.getName(), taskManager.getTasksById(task1.getId()).getName());
    }

    @Test
    void addEpic() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T02:30"), Duration.parse("PT5M"));//3
        taskManager.addTask(epic1);
        assertEquals(epic1.getName(), taskManager.getTasksById(epic1.getId()).getName());
    }

    @Test
    void addSubTask() {
        TaskManager taskManager = Managers.getDefault();
        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T05:30"), Duration.parse("PT5M"), 3);//6
        taskManager.addTask(subTask1);
        assertEquals(subTask1.getName(), taskManager.getTasksById(subTask1.getId()).getName());
    }


}