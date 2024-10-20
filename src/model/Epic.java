package model;

import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<Integer> listSubTasksId;

    public Epic(String name, String description, Status statusOfEpic) {
        super(name, description, statusOfEpic);
        //this.subTasksId = new HashMap<>();
        this.listSubTasksId = new ArrayList<>();
    }

    public Epic(String name, String description, Status statusOfEpic, int id) {
        super(name, description, statusOfEpic, id);
        //this.subTasksId = new HashMap<>();
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
