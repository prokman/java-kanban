package model;

import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<Integer> ListSubTasksId;



    public Epic(String name, String description, Status statusOfEpic) {
        super(name, description, statusOfEpic);
        //this.subTasksId = new HashMap<>();
        this.ListSubTasksId =new ArrayList<>();
    }

    public ArrayList<Integer> getListSubTasksId() {
        return ListSubTasksId;
    }

    public void addSubTasks(Integer subTasksId) {
        this.ListSubTasksId.add(subTasksId);
//                put(subTask.getId(), subTask);
    }

    public void removeSubTasks(Integer subTasksId) {
        this.ListSubTasksId.remove(subTasksId);
    }

    public void updateSubTasks(Integer subTasksId) {
        int indexOfsubTasksId=this.ListSubTasksId.indexOf(subTasksId);
        this.ListSubTasksId.set(indexOfsubTasksId,subTasksId);

    }

}
