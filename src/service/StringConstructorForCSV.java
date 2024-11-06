package service;

import model.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class StringConstructorForCSV {

    protected static String toStringCSV(Task task) {
        String lineForPrint;
        if (task.getType().equals(TypeOfTask.EPIC)) {
            lineForPrint = task.getId() + "," + TypeOfTask.EPIC + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + ","
                    + "Null,"
                    + task.getDuration() + "," + task.getStartTime();
        } else if (task.getType().equals(TypeOfTask.SUBTASK)) {
            lineForPrint = task.getId() + "," + TypeOfTask.SUBTASK + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + "," + ((SubTask) task).getEpicId() + ","
                    + task.getDuration() + "," + task.getStartTime();
        } else {
            lineForPrint = task.getId() + "," + TypeOfTask.TASK + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + ","
                    + "Null,"
                    + task.getDuration() + "," + task.getStartTime();
        }
        return lineForPrint;
    }

    protected static Task fromStringCSV(String value) {
        int fieldNumber = 0;

        StringBuilder sbId = new StringBuilder();//fieldNumber-0
        StringBuilder sbType = new StringBuilder();//fieldNumber-1
        StringBuilder sbName = new StringBuilder();//fieldNumber-2
        StringBuilder sbStatus = new StringBuilder();//fieldNumber-3
        StringBuilder sbDescription = new StringBuilder();//fieldNumber-4
        StringBuilder sbEpicId = new StringBuilder();//fieldNumber-5
        StringBuilder sbDuration = new StringBuilder();//fieldNumber-6
        StringBuilder sbStartTime = new StringBuilder();//fieldNumber-7

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
            } else if (value.charAt(i) != ',' && fieldNumber == 6) {
                sbDuration.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            } else if (value.charAt(i) != ',' && fieldNumber == 7) {
                sbStartTime.append(value.charAt(i));
                if (i != value.length() - 1) {
                    if (value.charAt(i + 1) == ',') fieldNumber++;
                }
            }
        }

        ////Создаем таски/сабтаски/эпики с использованием заготовленных полей
        if (sbType.toString().equals(TypeOfTask.TASK.toString())) {
            int id = Integer.parseInt(sbId.toString());
            Task task = new Task(sbName.toString(), sbDescription.toString(),
                    Status.valueOf(sbStatus.toString()),
                    LocalDateTime.parse(sbStartTime.toString()), Duration.parse(sbDuration.toString()), id);
            return task;
        } else if (sbType.toString().equals(TypeOfTask.SUBTASK.toString())) {
            int id = Integer.parseInt(sbId.toString());
            int epicId = Integer.parseInt(sbEpicId.toString());
            SubTask subTask = new SubTask(sbName.toString(), sbDescription.toString(),
                    Status.valueOf(sbStatus.toString()),
                    LocalDateTime.parse(sbStartTime.toString()), Duration.parse(sbDuration.toString()),
                    id, epicId);
            return subTask;
        } else {
            int id = Integer.parseInt(sbId.toString());
            Epic epic = new Epic(sbName.toString(), sbDescription.toString(),
                    Status.valueOf(sbStatus.toString()),
                    LocalDateTime.parse(sbStartTime.toString()), Duration.parse(sbDuration.toString()),
                    id);
            return epic;
        }
    }
}
