package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;
    private final HistoryManager historyManager;

    int id = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = historyManager;
    }

    private int genId() {
        return id++;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы получения списка всех задач
    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления всех задач
    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void clearSubTasks() {
        subTasks.clear();
        epics.clear();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы получения по идентификатору
    @Override
    public Task getTasksById(Integer id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicsById(Integer id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTasksById(Integer id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы создания
    @Override
    public Task addTask(Task task) {
        task.setId(genId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(genId());
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask subTask) {
        subTask.setId(genId());
        subTasks.put(subTask.getId(), subTask);
        final Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTasks(subTask.getId());
        epic.setStatus(getEpicStatus(epic));

        return subTask;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы обновления
    @Override
    public void updateTask(Task task) {
        final Task existTuskForUpdate = tasks.get(task.getId());
        if (existTuskForUpdate != null) {
            existTuskForUpdate.setName(task.getName());
            existTuskForUpdate.setDescription(task.getDescription());
            existTuskForUpdate.setStatus(task.getStatus());
            tasks.put(existTuskForUpdate.getId(), existTuskForUpdate);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        final Epic existEpicForUpdate = epics.get(epic.getId());
        if (existEpicForUpdate != null) {
            existEpicForUpdate.setName(epic.getName());
            existEpicForUpdate.setDescription(epic.getDescription());
            epics.put(existEpicForUpdate.getId(), existEpicForUpdate);
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        final SubTask existSubTaskForUpdate = subTasks.get(subTask.getId());
        if (existSubTaskForUpdate != null) {
            existSubTaskForUpdate.setName(subTask.getName());
            existSubTaskForUpdate.setDescription(subTask.getDescription());
            existSubTaskForUpdate.setStatus(subTask.getStatus());
            final Epic epic = epics.get(subTask.getEpicId());
            //epic.updateSubTasks(existSubTaskForUpdate.getId());
            epic.setStatus(getEpicStatus(epic));
            epics.put(epic.getId(), epic);
            subTasks.put(existSubTaskForUpdate.getId(), existSubTaskForUpdate);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления по идентификатору
    @Override
    public void removeTasksById(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicsById(Integer id) {
        final Epic epic = epics.remove(id);
        final ArrayList<Integer> SubTaskForDel = epic.getListSubTasksId();
        for (Integer idSubTaskForDel : SubTaskForDel) {
            subTasks.remove(SubTaskForDel.get(idSubTaskForDel));
        }
    }

    @Override
    public void removeSubTasksById(Integer id) {
        final SubTask remmovedSubTask = subTasks.remove(id);
        final int epicId = remmovedSubTask.getEpicId();
        Epic epic = epics.get(epicId);
        epic.removeSubTasks(id);
        epic.setStatus(getEpicStatus(epic));
        epics.put(epic.getId(), epic);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //получение списка всех поздадач эпика
    @Override
    public HashMap<Integer, SubTask> getSubTaskByEpic(Integer epicId) {
        Epic epic = epics.get(epicId);
        final ArrayList<Integer> SubTasksId = epic.getListSubTasksId();
        HashMap<Integer, SubTask> subTaskOfEpic = new HashMap<>();
        for (Integer id : SubTasksId) {
            subTaskOfEpic.put(id, subTasks.get(id));
        }
        return subTaskOfEpic;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //расчет статуса епика

    public Status getEpicStatus(Epic epic) {
        boolean isNew = true;
        boolean isDone = true;

        final ArrayList<Integer> ListSubTasksId = epic.getListSubTasksId();
        if (ListSubTasksId.isEmpty()) {
            return Status.NEW;
        } else {
            for (int id : ListSubTasksId) {

                //SubTask subTask = SubTasks.get(id);
                SubTask subTask = subTasks.get(id);
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

    ///////////////////////////////////////////////
    //GetHistory
    public List<Task> getHistory() {
        return historyManager.getAll();

    }


}