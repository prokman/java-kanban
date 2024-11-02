package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> listSubTasksId;


    public Epic(String name, String description, Status statusOfEpic) {
        super(name, description, statusOfEpic);
        this.listSubTasksId = new ArrayList<>();
    }

    public Epic(String name, String description, Status statusOfEpic,
                LocalDateTime startTime, Duration duration) {
        super(name, description, statusOfEpic, startTime, duration);
        this.listSubTasksId = new ArrayList<>();
    }

    public Epic(String name, String description, Status statusOfEpic,
                LocalDateTime startTime, Duration duration,
                int id) {
        super(name, description, statusOfEpic, startTime, duration, id);
        this.listSubTasksId = new ArrayList<>();
    }

    public ArrayList<Integer> getListSubTasksId() {
        return listSubTasksId;
    }

    public void addSubTasks(Integer subTasksId) {
        if (subTasksId != this.getId()) {
            this.listSubTasksId.add(subTasksId);
        }
    }

    public void removeSubTasks(Integer subTasksId) {
        this.listSubTasksId.remove(subTasksId);
    }

    public TypeOfTask getType() {
        return TypeOfTask.EPIC;
    }
}
