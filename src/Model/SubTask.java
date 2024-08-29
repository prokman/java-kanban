package Model;

public class SubTask extends Task {
    private Epic epic;

    public SubTask(String name, String description, Status statusOfsubTask, Epic epic) {
        super(name, description, statusOfsubTask);
        this.epic = epic;
    }

    public SubTask(String name, String description, Status statusOfsubTask, int id, Epic epic) {
        super(name, description, statusOfsubTask, id);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
