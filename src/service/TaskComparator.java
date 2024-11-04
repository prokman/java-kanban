package service;

import model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (o1.getStartTime().isEqual(o2.getStartTime())) {
            return o1.getId()-o2.getId();
        } else {
            return  o1.getStartTime().compareTo(o2.getStartTime());
        }
    }
}
