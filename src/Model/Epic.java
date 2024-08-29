package Model;

import java.util.HashMap;

public class Epic extends Task {
    final private HashMap<Integer, SubTask> subTasks;

    public Epic(String name, String description, Status statusOfEpic) {
        super(name, description, statusOfEpic);
        this.subTasks = new HashMap<>();
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public void addSubTasks(SubTask subTask) {
        this.subTasks.put(subTask.getId(), subTask);
    }

    public void removeSubTasks(SubTask subTask) {
        this.subTasks.remove(subTask.getId());
    }

    public void updateSubTasks(SubTask subTask) {
        this.subTasks.put(subTask.getId(), subTask);
    }

}
