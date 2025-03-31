package org.example.datamodel;

import java.io.Serializable;

public abstract sealed class Task implements Serializable permits ComplexTask, SimpleTask {
    private static final long serialVersionUID = 1L;
    private int idTask;
    private String statusTask;

    public Task(int idTask, String statusTask) {
        this.idTask = idTask;
        this.statusTask = statusTask;
    }
    public Task(){
    }
    public Task(String statusTask) {
        this.statusTask = statusTask;
    }

    public int getIdTask() {
        return idTask;
    }


    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public abstract int estimateDuration();
    public String toString() {
        return "TaskId: " + idTask + " Duration: " + estimateDuration() + " StatusTask: " + statusTask;
    }
}
