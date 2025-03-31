package org.example.datamodel;


import java.util.ArrayList;
import java.util.List;

public final class ComplexTask extends Task  {

    private List<Task> tasks;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
    }
    public ComplexTask(String statusTask) {
        super(statusTask);
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }
    public void deleteTask(Task task) {
        if(tasks != null) {
            tasks.remove(task);
        }
    }

    @Override
    public int estimateDuration() {
        if(tasks == null)
            return 0;
       final int[] totalDuration = {0};
       tasks.forEach(task -> {totalDuration[0] += task.estimateDuration();});
       return totalDuration[0];
    }
}

