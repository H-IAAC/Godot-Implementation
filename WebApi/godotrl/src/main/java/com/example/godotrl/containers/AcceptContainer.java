package com.example.godotrl.containers;

public class AcceptContainer {
    private final long id;
    private final String message;

    public AcceptContainer(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
