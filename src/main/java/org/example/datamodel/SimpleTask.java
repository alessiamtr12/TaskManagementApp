package org.example.datamodel;



public final class SimpleTask extends Task {

    private int startHour;
    private int endHour;

    public SimpleTask(int startHour, int endHour, int idTask, String statusTask) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }
    public SimpleTask(int startHour, int endHour, String statusTask){
        super(statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }


    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }
}

