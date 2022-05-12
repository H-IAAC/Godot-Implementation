package com.example.cstgodottest.container;

public class TestContainer {

    private final long id;
    private final String message;

    public TestContainer(long id, String message) {
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
