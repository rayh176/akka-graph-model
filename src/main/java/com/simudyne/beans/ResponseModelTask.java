package com.simudyne.beans;

public class ResponseModelTask {

    private RequestModelTask requestModelTask;
    private double affinity;

    public ResponseModelTask(RequestModelTask requestModelTask, double affinity) {
        this.requestModelTask = requestModelTask;
        this.affinity = affinity;
    }

    public RequestModelTask getRequestModelTask() {
        return requestModelTask;
    }

    public double getAffinity() {
        return affinity;
    }
}
