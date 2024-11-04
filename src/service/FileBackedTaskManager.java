package service;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private String fileName;

    public FileBackedTaskManager(HistoryManager historyManager, String fileName) {
        super(historyManager);
        this.fileName = fileName;
    }

    private void save() throws ManagerSaveException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("Файл по указанному пути не найден");
            return;
        }
        try (FileWriter fr = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fr)) {
            for (Integer id : tasks.keySet()) {
                bw.write(StringConstructorForCSV.toStringCSV(tasks.get(id)));
                bw.newLine();
            }
            for (Integer id : epics.keySet()) {
                bw.write(StringConstructorForCSV.toStringCSV(epics.get(id)));
                bw.newLine();
            }
            for (Integer id : subTasks.keySet()) {
                bw.write(StringConstructorForCSV.toStringCSV(subTasks.get(id)));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException("Ошибка в файле" + path);
        }
    }

    private void loadFromFile() {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("Файл по указанному пути не найден");
            return;
        }
        try {
            if (Files.size(path) == 0) {
                System.out.println("Файл пустой");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ошибка при проверке файла на пустоту");
        }

        try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) {
            int maxId = 0;
            while (br.ready()) {
                String taskLine = br.readLine();
                Task taskForLoad = StringConstructorForCSV.fromStringCSV(taskLine);
                if (maxId < taskForLoad.getId()) maxId = taskForLoad.getId();
                if (taskForLoad.getType().equals(TypeOfTask.EPIC)) {
                    epics.put(taskForLoad.getId(), (Epic) taskForLoad);
                } else if (taskForLoad.getType().equals(TypeOfTask.SUBTASK)) {
                    subTasks.put(taskForLoad.getId(), (SubTask) taskForLoad);
                    prioritizedTasks.add((SubTask) taskForLoad);
                } else {
                    tasks.put(taskForLoad.getId(), taskForLoad);
                    prioritizedTasks.add(taskForLoad);
                }
            }
            super.id = maxId + 1;
            for (Integer subTuskId : subTasks.keySet()) {
                final Epic epic = epics.get(subTasks.get(subTuskId).getEpicId());
                epic.addSubTasks(subTuskId);
            }
            for (Integer epicId : epics.keySet()) {
                final Epic epic = epics.get(epicId);
                setEpicStatuses(epic);
                prioritizedTasks.add(epic);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ошибка при чтении файла");
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            System.out.println("ошибка при парсинге строк файла");
        }
    }

    public static FileBackedTaskManager loadFromFile(String fileName) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(new InMemoryHistoryManager(), fileName);
        fileBackedTaskManager.loadFromFile();
        return fileBackedTaskManager;
    }

    ///////////////////////////////////////////////////
    //методы удаления всех задач
    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubTasks() {
        super.clearSubTasks();
        save();
    }

    ///////////////////////////////////////////////////
    //методы создания
    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
        return subTask;
    }

    ///////////////////////////////////////////////////
    //методы обновления
    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    ///////////////////////////////////////////////////
    //методы удаления по идентификатору
    @Override
    public void removeTasksById(Integer id) {
        super.removeTasksById(id);
        save();
    }

    @Override
    public void removeEpicsById(Integer id) {
        super.removeEpicsById(id);
        save();
    }

    @Override
    public void removeSubTasksById(Integer id) {
        super.removeSubTasksById(id);
        save();
    }
}
