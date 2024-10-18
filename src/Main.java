import model.Status;
import model.SubTask;
import service.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        System.out.println("файл загружен");
        System.out.println("вывод тасков- " + taskManager.getTasks());
        System.out.println();
        System.out.println("вывод епиков- " + taskManager.getEpics());
        System.out.println();
        System.out.println("вывод сабтасков- " + taskManager.getSubTasks());
        System.out.println();

        SubTask subTask10 = new SubTask("subtask10", "StDescript10", Status.IN_PROGRESS, 4);
        //taskManager.updateSubTask(subTask10);
        taskManager.addSubTask(subTask10);
        //taskManager.removeSubTasksById(15);

        System.out.println();
        System.out.println("вывод сабтасков- " + taskManager.getSubTasks());


        ///блок для загрузки задач
       /* Task task1 = new Task("Task1", "TDescript1", Status.NEW);//0
        Task task2 = new Task("Task2", "TDescript2", Status.NEW);//1
        Task task3 = new Task("Task3", "TDescript3", Status.NEW);//2

        Epic epic1 = new Epic("Epic1", "EDescript1", Status.NEW);//3
        Epic epic2 = new Epic("Epic2", "EDescript2", Status.NEW);//4
        Epic epic3 = new Epic("Epic3", "EDescript3", Status.NEW);//5

        SubTask subTask1 = new SubTask("subtask1", "StDescript1", Status.NEW, 3);//6
        SubTask subTask2 = new SubTask("subtask2", "StDescript2", Status.NEW, 3);//7
        SubTask subTask3 = new SubTask("subtask3", "StDescript3", Status.NEW, 3);//8

        SubTask subTask4 = new SubTask("subtask4", "StDescript4", Status.NEW, 4);//9
        SubTask subTask5 = new SubTask("subtask5", "StDescript5", Status.NEW, 4);//10
        SubTask subTask6 = new SubTask("subtask6", "StDescript6", Status.NEW, 4);//11

        SubTask subTask7 = new SubTask("subtask7", "StDescript4", Status.NEW, 5);//12
        SubTask subTask8 = new SubTask("subtask8", "StDescript5", Status.NEW, 5);//13
        SubTask subTask9 = new SubTask("subtask9", "StDescript6", Status.NEW, 5);//14
        System.out.println();
        System.out.println("успешно созданы объекты");

       //помещение объектов в таблицы
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        System.out.println();
        System.out.println("вывод тасков- " + taskManager.getTasks());
        System.out.println();

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);

        System.out.println("вывод епиков- " + taskManager.getEpics());
        System.out.println();

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.addSubTask(subTask4);
        taskManager.addSubTask(subTask5);
        taskManager.addSubTask(subTask6);

        taskManager.addSubTask(subTask7);
        taskManager.addSubTask(subTask8);
        taskManager.addSubTask(subTask9);

        System.out.println("вывод сабтасков- " + taskManager.getSubTasks());*/
        ///конец блока для загрузки задач

    }
}