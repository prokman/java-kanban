import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1= new Task("Task1","TDescript1", Status.NEW);
        Task task2= new Task("Task2","TDescript2", Status.NEW);
        Task task3= new Task("Task3","TDescript3", Status.NEW);

        Epic epic1=new Epic("Epic1", "EDescript1",Status.NEW);
        Epic epic2=new Epic("Epic2", "EDescript2",Status.NEW);
        Epic epic3=new Epic("Epic3", "EDescript3",Status.NEW);

        SubTask subTask1=new SubTask("subtask1", "StDescript1",Status.NEW,3);
        SubTask subTask2=new SubTask("subtask2", "StDescript2",Status.NEW,3);
        SubTask subTask3=new SubTask("subtask3", "StDescript3",Status.NEW,3);

        SubTask subTask4=new SubTask("subtask4", "StDescript4",Status.NEW,4);
        SubTask subTask5=new SubTask("subtask5", "StDescript5",Status.NEW,4);
        SubTask subTask6=new SubTask("subtask6", "StDescript6",Status.NEW,4);

        SubTask subTask7=new SubTask("subtask7", "StDescript4",Status.NEW,5);
        SubTask subTask8=new SubTask("subtask8", "StDescript5",Status.NEW,5);
        SubTask subTask9=new SubTask("subtask9", "StDescript6",Status.NEW,5);
        System.out.println();
        System.out.println("успешно созданы объекты");

        ///////////////////////////////////////////////////////
        //проверка вывода
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        System.out.println();
        System.out.println("вывод тасков- "+taskManager.getTasks());
        System.out.println();

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);


        System.out.println("вывод епиков- "+taskManager.getEpics());
        System.out.println();

        taskManager.addSubTask(subTask1);


        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        System.out.println("вывод сабтасков- "+taskManager.getSubTasks());

        /////////////////////////////////////////////////////////////////
        //проверка обновления
        SubTask subTask10=new SubTask("subtask10", "StDescript10",Status.IN_PROGRESS,3);
        taskManager.addSubTask(subTask10);
        System.out.println();
        System.out.println("Добавили сабатаск 10 статус прогрес - "+taskManager.getSubTasks());
        System.out.println();

        System.out.println("вывод обновленных епиков (1 стал прогрес))- "+taskManager.getEpics());

        subTask1=new SubTask("subtask1up", "StDescript1up",Status.DONE, 6, 3);
        subTask2=new SubTask("subtask2up", "StDescript2up",Status.DONE, 7, 3);
        subTask3=new SubTask("subtask3up", "StDescript3up",Status.DONE, 8, 3);

        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);
        taskManager.removeSubTasksById(9);//удалем 10 сабТаск (id-9)
        System.out.println();
        System.out.println("обновили сабтаски 1-3 (у всех доне статус), удалили сабтаск 10");


        System.out.println();
        System.out.println("вывод обновленных епиков - "+taskManager.getEpics());

        System.out.println();
        System.out.println("вывод обновленных сабтасков - "+taskManager.getSubTasks());

        System.out.println();
        System.out.println("вывод Сабтасков по епику"+taskManager.getSubTaskByEpic(3));


        System.out.println();
        System.out.println("вывод таск по ид "+taskManager.getTasksById(1));
        System.out.println();
        System.out.println("вывод эпик по ид "+taskManager.getEpicsById(3));
        System.out.println();
        System.out.println("вывод Сабтасков по ид "+taskManager.getSubTasksById(7));

        taskManager.clearTasks();
        taskManager.clearEpics();
        System.out.println("очистка");
        System.out.println("вывод тасков- "+taskManager.getTasks());
        System.out.println("вывод епиков- "+taskManager.getEpics());
        System.out.println("вывод сабтасков- "+taskManager.getSubTasks());
    }
}