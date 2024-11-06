import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        ///блок для загрузки задач
        Task task1 = new Task("Task1", "TDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T00:30"), Duration.parse("PT5M"));//0
        Task task2 = new Task("Task2", "TDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T00:20"), Duration.parse("PT5M"));//1
        Task task3 = new Task("Task3", "TDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T00:00"), Duration.parse("PT5M"));//2

        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T02:30"), Duration.parse("PT5M"));//3
        Epic epic2 = new Epic("Epic2", "EDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T02:20"), Duration.parse("PT5M"));//4
        Epic epic3 = new Epic("Epic3", "EDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T02:10"), Duration.parse("PT5M"));//5

        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, LocalDateTime.parse("2024-10-31T05:30"), Duration.parse("PT5M"), 3);//6
        SubTask subTask2 = new SubTask("subtask2", "StDescript2", Status.NEW, LocalDateTime.parse("2024-10-31T05:40"), Duration.parse("PT5M"), 3);//7
        SubTask subTask3 = new SubTask("subtask3", "StDescript3", Status.NEW, LocalDateTime.parse("2024-10-31T05:51"), Duration.parse("PT5M"), 3);//8

        SubTask subTask4 = new SubTask("subtask4", "StDescript4", Status.NEW, LocalDateTime.parse("2024-10-31T01:00"), Duration.parse("PT5M"), 4);//9
        SubTask subTask5 = new SubTask("subtask5", "StDescript5", Status.NEW, LocalDateTime.parse("2024-10-31T01:10"), Duration.parse("PT5M"), 4);//10
        SubTask subTask6 = new SubTask("subtask6", "StDescript6", Status.NEW, LocalDateTime.parse("2024-10-31T01:20"), Duration.parse("PT5M"), 4);//11

        SubTask subTask7 = new SubTask("subtask7", "StDescript4", Status.NEW, LocalDateTime.parse("2024-10-31T01:30"), Duration.parse("PT5M"), 5);//12
        SubTask subTask8 = new SubTask("subtask8", "StDescript5", Status.NEW, LocalDateTime.parse("2024-10-31T01:40"), Duration.parse("PT5M"), 5);//13
        SubTask subTask9 = new SubTask("subtask9", "StDescript6", Status.NEW, LocalDateTime.parse("2024-10-31T01:50"), Duration.parse("PT5M"), 5);//14
        System.out.println();
        System.out.println("успешно созданы объекты");

        //помещение объектов в таблицы
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        taskManager.addSubTask(subTask4);
        taskManager.addSubTask(subTask5);
        taskManager.addSubTask(subTask6);
        taskManager.addSubTask(subTask7);
        taskManager.addSubTask(subTask8);
        taskManager.addSubTask(subTask9);

        System.out.println();
        System.out.println("вывод сабтасков-" + taskManager.getSubTasks());
        Task task4 = new Task("Task3Up", "TDescript3Up", Status.NEW, LocalDateTime.parse("2024-10-29T00:00"), Duration.parse("PT5M"), 2);//2
        taskManager.updateTask(task4);
        System.out.println();
        System.out.println("сортированный список-" + taskManager.getPrioritizedTasks());
        System.out.println();
        System.out.println();
        ///конец блока для загрузки задач


    }
}