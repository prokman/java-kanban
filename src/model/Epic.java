package model;

import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<Integer> ListSubTasksId;

    public Epic(String name, String description, Status statusOfEpic) {
        super(name, description, statusOfEpic);
        //this.subTasksId = new HashMap<>();
        this.ListSubTasksId = new ArrayList<>();
    }

    public ArrayList<Integer> getListSubTasksId() {
        return ListSubTasksId;
    }

    public void addSubTasks(Integer subTasksId) {
        if (subTasksId!=this.getId()) {
        this.ListSubTasksId.add(subTasksId);
        }
    }

    public void removeSubTasks(Integer subTasksId) {
        this.ListSubTasksId.remove(subTasksId);
    }


}