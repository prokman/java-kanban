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
        InMemoryTaskManager TM = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task1= new Task("Task1","TDescript1", Status.NEW);
        TM.addTask(task1);
        assertEquals(task1.getName(), TM.getTasksById(task1.getId()).getName());
 }

    @Test
    void addEpicTest() {
        InMemoryTaskManager TM = new InMemoryTaskManager(new InMemoryHistoryManager());
        Epic epic1=new Epic("Epic1", "EDescript1",Status.NEW);
        TM.addTask(epic1);
        assertEquals(epic1.getName(), TM.getTasksById(epic1.getId()).getName());
    }

    @Test
    void addSubTask() {
        InMemoryTaskManager TM = new InMemoryTaskManager(new InMemoryHistoryManager());
        SubTask subTask1=new SubTask("subtask1", "StDescript1",Status.NEW,1);
        TM.addTask(subTask1);
        assertEquals(subTask1.getName(), TM.getTasksById(subTask1.getId()).getName());
    }



}