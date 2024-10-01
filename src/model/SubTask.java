package model;

public class SubTask extends Task {
    int epicId;

    public SubTask(String name, String description, Status statusOfsubTask, int epicId) {
        super(name, description, statusOfsubTask);
        if (this.getId() != epicId) {
            this.epicId = epicId;
        } else {
            this.epicId = -1;
        }

    }

    public SubTask(String name, String description, Status statusOfsubTask, int id, int epicId) {
        super(name, description, statusOfsubTask, id);
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
}
