package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    /*default int genId() {
        return id++;
    }*/

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы получения списка всех задач
    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, SubTask> getSubTasks();

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления всех задач
    void clearTasks();

    void clearEpics();

    void clearSubTasks();

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы получения по идентификатору
    Task getTasksById(Integer id);

    Epic getEpicsById(Integer id);

    SubTask getSubTasksById(Integer id);

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы создания
    Task addTask(Task task);

    Epic addEpic(Epic epic);

    SubTask addSubTask(SubTask subTask);

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы обновления
    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления по идентификатору
    void removeTasksById(Integer id);

    void removeEpicsById(Integer id);

    void removeSubTasksById(Integer id);

    ///////////////////////////////////////////////////////////////////////////////////////////
    //получение списка всех поздадач эпика
    HashMap<Integer, SubTask> getSubTaskByEpic(Integer epicId);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();


}
