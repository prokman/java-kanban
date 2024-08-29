package Service;

import Model.Epic;
import Model.Status;
import Model.SubTask;
import Model.Task;

import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTask> subTasks;
    int id = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }

    private int genId() {
        return id++;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы получения списка всех задач
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления всех задач
    public void clearTasks() {
        tasks.clear();
    }

    public void clearEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void clearSubTasks() {
        subTasks.clear();
        epics.clear();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы получения по идентификатору
    public Task getTasksById(Integer id) {
        return tasks.get(id);
    }

    public Epic getEpicsById(Integer id) {
        return epics.get(id);
    }

    public SubTask getSubTasksById(Integer id) {
        return subTasks.get(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы создания
    public Task addTask(Task task) {
        task.setId(genId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addEpic(Epic epic) {
        epic.setId(genId());
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask addSubTask(SubTask subTask) {
        subTask.setId(genId());
        final Epic epic = epics.get(subTask.getEpic().getId());
        epic.addSubTasks(subTask);
        epic.setStatus(getEpicStatus(epic));
        subTasks.put(subTask.getId(), subTask);
        return subTask;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы обновления
    public void updateTask(Task task) {
        final Task existTuskForUpdate = tasks.get(task.getId());
        if (existTuskForUpdate != null) {
            existTuskForUpdate.setName(task.getName());
            existTuskForUpdate.setDescription(task.getDescription());
            existTuskForUpdate.setStatus(task.getStatus());
            tasks.put(existTuskForUpdate.getId(), existTuskForUpdate);
        }
    }

    public void updateEpic(Epic epic) {
        final Epic existEpicForUpdate = epics.get(epic.getId());
        if (existEpicForUpdate != null) {
            existEpicForUpdate.setName(epic.getName());
            existEpicForUpdate.setDescription(epic.getDescription());
            epics.put(existEpicForUpdate.getId(), existEpicForUpdate);
        }
    }

    public void updateSubTask(SubTask subTask) {
        final SubTask existSubTaskForUpdate = subTasks.get(subTask.getId());
        if (existSubTaskForUpdate != null) {
            existSubTaskForUpdate.setName(subTask.getName());
            existSubTaskForUpdate.setDescription(subTask.getDescription());
            existSubTaskForUpdate.setStatus(subTask.getStatus());
            final Epic epic = epics.get(subTask.getEpic().getId());
            epic.updateSubTasks(existSubTaskForUpdate);
            epic.setStatus(getEpicStatus(epic));
            epics.put(epic.getId(), epic);
            subTasks.put(existSubTaskForUpdate.getId(), existSubTaskForUpdate);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления по идентификатору
    public void removeTasksById(Integer id) {
        tasks.remove(id);
    }

    public void removeEpicsById(Integer id) {
        final Epic epic = epics.remove(id);
        HashMap<Integer, SubTask> SubTaskForDel = epic.getSubTasks();
        for (Integer idSubTaskForDel : SubTaskForDel.keySet()) {
            subTasks.remove(idSubTaskForDel);
        }
    }

    public void removeSubTasksById(Integer id) {
        final SubTask remmovedSubTask = subTasks.remove(id);
        final Epic epic = remmovedSubTask.getEpic();
        epic.removeSubTasks(remmovedSubTask);
        epic.setStatus(getEpicStatus(epic));
        epics.put(epic.getId(), epic);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //получение списка всех поздадач эпика
    public HashMap<Integer, SubTask> getSubTaskByEpic(Integer EpicId) {
        Epic epic = epics.get(EpicId);
        final HashMap<Integer, SubTask> SubTasks = epic.getSubTasks();
        return SubTasks;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //расчет статуса епика
    public Status getEpicStatus(Epic epic) {
        boolean isNew = true;
        boolean isDone = true;

        final HashMap<Integer, SubTask> SubTasks = epic.getSubTasks();
        if (SubTasks.isEmpty()) {
            return Status.NEW;
        } else {
            for (int id : SubTasks.keySet()) {

                SubTask subTask = SubTasks.get(id);
                isNew = isNew && subTask.getStatus().equals(Status.NEW);
                isDone = isDone && subTask.getStatus().equals(Status.DONE);
            }
            if (isNew) {
                return Status.NEW;
            } else if (isDone) {
                return Status.DONE;
            } else {
                return Status.IN_PROGRESS;
            }
        }
    }
}