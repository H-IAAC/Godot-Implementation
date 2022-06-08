package com.example.godotrl.containers;

import com.example.godotrl.util.RequestType;

public class AcceptContainer {
    private final long id;
    private final String message;
    private final RequestType requestType;

    public AcceptContainer(long id, String message, RequestType requestType) {
        this.id = id;
        this.message = message;
        this.requestType = requestType;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
