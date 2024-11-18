package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Epic> epics;
    protected final HashMap<Integer, SubTask> subTasks;
    protected final TaskComparator taskComparator = new TaskComparator();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(taskComparator);
    protected final HistoryManager historyManager;
    protected int id = 0;

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
        for (Integer id : tasks.keySet()) {
            prioritizedTasks.remove(tasks.get(id));
        }
        tasks.clear();
    }

    @Override
    public void clearEpics() {
        for (Integer id : subTasks.keySet()) {
            prioritizedTasks.remove(subTasks.get(id));
        }
        for (Integer id : epics.keySet()) {
            prioritizedTasks.remove(epics.get(id));
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void clearSubTasks() {
        for (Integer id : subTasks.keySet()) {
            prioritizedTasks.remove(subTasks.get(id));
        }
        for (Integer id : epics.keySet()) {
            prioritizedTasks.remove(epics.get(id));
        }
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
        if (isAnyPrioritizedTaskCross(task)) {
            System.out.println("Задача task id-" + task.getId() + " пересекается с существующими");
            id--;
            throw new ManagerSaveException("пересечение");
        } else {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        }
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(genId());
        setEpicStatuses(epic);
        if (isAnyPrioritizedTaskCross(epic)) {
            System.out.println("Задача epic id-" + epic.getId() + " пересекается с существующими");
            id--;
            throw new ManagerSaveException("пересечение");
        } else {
            epics.put(epic.getId(), epic);
            prioritizedTasks.add(epic);
        }
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask subTask) {
        subTask.setId(genId());
        if (isAnyPrioritizedTaskCross(subTask)) {
            System.out.println("Задача subTask id" + subTask.getId() + " пересекается с существующими");
            id--;
            throw new ManagerSaveException("пересечение");
        } else {
            subTasks.put(subTask.getId(), subTask);
            final Epic epic = epics.get(subTask.getEpicId());
            prioritizedTasks.remove(epic);
            epic.addSubTasks(subTask.getId());
            setEpicStatuses(epic);
            prioritizedTasks.add(epic);
            prioritizedTasks.add(subTask);
        }
        return subTask;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы обновления
    @Override
    public void updateTask(Task task) {
        if (isAnyPrioritizedTaskCross(task)) {
            System.out.println("Обновление task id-" + task.getId() + " пересекается с существующими");
            throw new ManagerSaveException("пересечение");
        } else {
            final Task existTuskForUpdate = tasks.get(task.getId());
            if (existTuskForUpdate != null) {
                prioritizedTasks.remove(existTuskForUpdate);
                existTuskForUpdate.setName(task.getName());
                existTuskForUpdate.setDescription(task.getDescription());
                existTuskForUpdate.setStatus(task.getStatus());
                existTuskForUpdate.setStartTime(task.getStartTime());
                existTuskForUpdate.setDuration(task.getDuration());
                tasks.put(existTuskForUpdate.getId(), existTuskForUpdate);
                prioritizedTasks.add(existTuskForUpdate);
            }
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (isAnyPrioritizedTaskCross(epic)) {
            System.out.println("Обновление epic id-" + epic.getId() + " пересекается с существующими");
            throw new ManagerSaveException("пересечение");
        } else {
            final Epic existEpicForUpdate = epics.get(epic.getId());
            if (existEpicForUpdate != null) {
                prioritizedTasks.remove(existEpicForUpdate);
                existEpicForUpdate.setName(epic.getName());
                existEpicForUpdate.setDescription(epic.getDescription());
                epics.put(existEpicForUpdate.getId(), existEpicForUpdate);
                prioritizedTasks.add(existEpicForUpdate);
            }
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (isAnyPrioritizedTaskCross(subTask)) {
            System.out.println("Обновление subTask id-" + subTask.getId() + " пересекается с существующими");
            throw new ManagerSaveException("пересечение");
        } else {
            final SubTask existSubTaskForUpdate = subTasks.get(subTask.getId());
            if (existSubTaskForUpdate != null) {
                prioritizedTasks.remove(existSubTaskForUpdate);
                final Epic epic = epics.get(subTask.getEpicId());
                prioritizedTasks.remove(epic);
                existSubTaskForUpdate.setName(subTask.getName());
                existSubTaskForUpdate.setDescription(subTask.getDescription());
                existSubTaskForUpdate.setStatus(subTask.getStatus());
                existSubTaskForUpdate.setStartTime(subTask.getStartTime());
                existSubTaskForUpdate.setDuration(subTask.getDuration());
                existSubTaskForUpdate.setEpic(subTask.getEpicId());
                setEpicStatuses(epic);
                epics.put(epic.getId(), epic);
                subTasks.put(existSubTaskForUpdate.getId(), existSubTaskForUpdate);
                prioritizedTasks.add(existSubTaskForUpdate);
                prioritizedTasks.add(epic);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //методы удаления по идентификатору
    @Override
    public void removeTasksById(Integer id) {
        Task removedTask = tasks.remove(id);
        prioritizedTasks.remove(removedTask);
    }

    @Override
    public void removeEpicsById(Integer id) {
        final Epic epic = epics.remove(id);
        final ArrayList<Integer> SubTaskForDel = epic.getListSubTasksId();
        SubTask removedTask;
        for (Integer idSubTaskForDel : SubTaskForDel) {
            removedTask = subTasks.remove(idSubTaskForDel);
            prioritizedTasks.remove(removedTask);
        }
        prioritizedTasks.remove(epic);
    }

    @Override
    public void removeSubTasksById(Integer id) {
        final SubTask remmovedSubTask = subTasks.remove(id);
        final int epicId = remmovedSubTask.getEpicId();
        Epic epic = epics.get(epicId);
        prioritizedTasks.remove(epic);
        epic.removeSubTasks(id);
        setEpicStatuses(epic);
        prioritizedTasks.remove(remmovedSubTask);
        prioritizedTasks.add(epic);
        epics.put(epic.getId(), epic);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //получение списка всех поздадач эпика
    @Override
    public HashMap<Integer, SubTask> getSubTaskByEpic(Integer epicId) {
        Epic epic = epics.get(epicId);
        final ArrayList<Integer> SubTasksId = epic.getListSubTasksId();
        HashMap<Integer, SubTask> subTaskOfEpic = new HashMap<>();
        SubTasksId.stream()
                .forEach(id -> subTaskOfEpic.put(id, subTasks.get(id)));
        return subTaskOfEpic;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //расчет статуса епика

    public void setEpicStatuses(Epic epic) {
        boolean isNew = true;
        boolean isDone = true;
        final ArrayList<Integer> ListSubTasksId = epic.getListSubTasksId();
        LocalDateTime epicStartTime = LocalDateTime.MAX;
        LocalDateTime epicEndTime = LocalDateTime.MIN;
        Duration epicNewDuaration;

        if (ListSubTasksId.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            for (int id : ListSubTasksId) {
                //Расчет статуса эпика
                SubTask subTask = subTasks.get(id);
                isNew = isNew && subTask.getStatus().equals(Status.NEW);
                isDone = isDone && subTask.getStatus().equals(Status.DONE);

                //расчет времени начала, продолжительности
                LocalDateTime subTaskStartTime = subTask.getStartTime();
                Duration subTaskDuration = subTask.getDuration();
                LocalDateTime subTaskEndTime = subTaskStartTime.plus(subTaskDuration);
                if (subTaskStartTime.isBefore(epicStartTime)) {
                    epicStartTime = subTaskStartTime;
                }
                //Для размещения в TreeSet Эпика, которому принадлежит один сабтаск,
                //добавляю небольшую разницу во времени
                if (subTaskEndTime.isAfter(epicEndTime)) {
                    epicEndTime = subTaskEndTime;
                }
            }
            if (isNew) {
                epic.setStatus(Status.NEW);
            } else if (isDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            epic.setStartTime(epicStartTime);
            epicNewDuaration = Duration.between(epicStartTime, epicEndTime);
            epic.setDuration(epicNewDuaration);
        }
    }

    ///////////////////////////////////////////////
    //GetHistory
    public List<Task> getHistory() {
        return historyManager.getAll();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private boolean isCrossed(Task task1, Task task2) {
        boolean crossed = false;
        if (task1.getStartTime().isBefore(task2.getStartTime())) {
            crossed = task1.getStartTime().plus(task1.getDuration()).isAfter(task2.getStartTime()) ||
                    task1.getStartTime().plus(task1.getDuration()).isEqual(task2.getStartTime());

        } else if (task2.getStartTime().isBefore(task1.getStartTime())) {
            crossed = task2.getStartTime().plus(task2.getDuration()).isAfter(task1.getStartTime()) ||
                    task2.getStartTime().plus(task2.getDuration()).isEqual(task1.getStartTime());
        } else crossed = true;
        return crossed;
    }

    private boolean isAnyPrioritizedTaskCross(Task task) {
        for (Task t : prioritizedTasks) {
            if (t.getId() == task.getId()) continue;
            if (isCrossed(task, t)) {
                System.out.println(t.getType() + " id-" + t.getId() + " crossed " + task.getType() + " id-" + task.getId());
                return true;
            }
        }
        return false;
    }
}