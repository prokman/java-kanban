package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, Status statusOfsubTask, int epicId) {
        super(name, description, statusOfsubTask);
        if (this.getId() != epicId) {
            this.epicId = epicId;
        } else {
            this.epicId = -1;
        }
    }

    public SubTask(String name, String description, Status statusOfsubTask,
                   LocalDateTime startTime, Duration duration, int epicId) {
        super(name, description, statusOfsubTask, startTime, duration);
        if (this.getId() != epicId) {
            this.epicId = epicId;
        } else {
            this.epicId = -1;
        }
    }

    public SubTask(String name, String description, Status statusOfsubTask,
                   LocalDateTime startTime, Duration duration, int id, int epicId) {
        super(name, description, statusOfsubTask, startTime, duration, id);
        if (this.getId() != epicId) {
            this.epicId = epicId;
        } else {
            this.epicId = -1;
        }
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpic(int epicId) {
        if (epicId != this.getId()) {
            this.epicId = epicId;
        }
    }

    public TypeOfTask getType() {
        return TypeOfTask.SUBTASK;
    }
}
