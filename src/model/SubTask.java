package model;

public class SubTask extends Task {
    int epicId;

    public SubTask(String name, String description, Status statusOfsubTask, int epicId) {
        super(name, description, statusOfsubTask);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, Status statusOfsubTask, int id, int epicId) {
        super(name, description, statusOfsubTask, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpic(int epicId) {
        this.epicId = epicId;
    }
}
