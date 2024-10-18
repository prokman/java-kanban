package service;

import model.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
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
                bw.write(toString(tasks.get(id)));
                bw.newLine();
            }
            for (Integer id : epics.keySet()) {
                bw.write(toString(epics.get(id)));
                bw.newLine();
            }
            for (Integer id : subTasks.keySet()) {
                bw.write(toString(subTasks.get(id)));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException();
        }
    }

    private String toString(Task task) {
        String lineForPrint;
        if (task instanceof Epic) {
            lineForPrint = task.getId() + "," + TypeOfTask.EPIC + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription();
        } else if (task instanceof SubTask) {
            lineForPrint = task.getId() + "," + TypeOfTask.SUBTASK + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "," + ((SubTask) task).getEpicId();
        } else {
            lineForPrint = task.getId() + "," + TypeOfTask.TASK + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription();
        }
        return lineForPrint;
    }


    private Task fromString(String value) {
        int fieldNumber = 0;

        StringBuilder sbId = new StringBuilder();//fieldNumber-0
        StringBuilder sbType = new StringBuilder();//fieldNumber-1
        StringBuilder sbName = new StringBuilder();//fieldNumber-2
        StringBuilder sbStatus = new StringBuilder();//fieldNumber-3
        StringBuilder sbDescription = new StringBuilder();//fieldNumber-4
        StringBuilder sbEpicId = new StringBuilder();//fieldNumber-5

        ////Разбираем строку на отдельные поля
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != ',' && fieldNumber == 0) {
                sbId.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            } else if (value.charAt(i) != ',' && fieldNumber == 1) {
                sbType.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            } else if (value.charAt(i) != ',' && fieldNumber == 2) {
                sbName.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            } else if (value.charAt(i) != ',' && fieldNumber == 3) {
                sbStatus.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            } else if (value.charAt(i) != ',' && fieldNumber == 4) {
                sbDescription.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            } else if (value.charAt(i) != ',' && fieldNumber == 5) {
                sbEpicId.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            }
        }

        ////Создаем таски/сабтаски/эпики с использованием заготовленных полей
        if (sbType.toString().equals(TypeOfTask.TASK.toString())) {
            int id = Integer.parseInt(sbId.toString());
            Task task = new Task(sbName.toString(), sbDescription.toString(), Status.valueOf(sbStatus.toString()), id);
            return task;
        } else if (sbType.toString().equals(TypeOfTask.SUBTASK.toString())) {
            int id = Integer.parseInt(sbId.toString());
            int epicId = Integer.parseInt(sbEpicId.toString());
            SubTask subTask = new SubTask(sbName.toString(), sbDescription.toString(), Status.valueOf(sbStatus.toString()), id, epicId);
            return subTask;
        } else {
            int id = Integer.parseInt(sbId.toString());
            Epic epic = new Epic(sbName.toString(), sbDescription.toString(), Status.valueOf(sbStatus.toString()), id);
            return epic;
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
                Task taskForLoad = fromString(taskLine);
                if (maxId < taskForLoad.getId()) maxId = taskForLoad.getId();
                if (taskForLoad instanceof Epic) {
                    epics.put(taskForLoad.getId(), (Epic) taskForLoad);
                } else if (taskForLoad instanceof SubTask) {
                    subTasks.put(taskForLoad.getId(), (SubTask) taskForLoad);
                } else {
                    tasks.put(taskForLoad.getId(), taskForLoad);
                }
            }
            super.id = maxId;
            for (Integer subTuskId : subTasks.keySet()) {
                //subTasks.put(subTask.getId(), subTask);
                final Epic epic = epics.get(subTasks.get(subTuskId).getEpicId());
                epic.addSubTasks(subTuskId);
                epic.setStatus(getEpicStatus(epic));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ошибка при чтении файла");
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
