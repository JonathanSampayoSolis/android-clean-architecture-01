package com.example.jjsampayo.mvvmsample1.util.network;

public class RequestState {
    private final Status status;
    private final String msg;

    public static final RequestState LOADED;
    public static final RequestState LOADING;

    public RequestState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    static {
        LOADED = new RequestState(Status.SUCCESS, "Success");
        LOADING = new RequestState(Status.RUNNING, "Running");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}