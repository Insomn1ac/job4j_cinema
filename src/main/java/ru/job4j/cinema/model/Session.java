package ru.job4j.cinema.model;

import java.io.Serializable;

public class Session implements Serializable {
    private int id;
    private String name;
    private String description;
    private String timeOfSession;
    private byte[] photo;

    public Session() {

    }

    public Session(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Session(int id, String name, String description, String timeOfSession, byte[] photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeOfSession = timeOfSession;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeOfSession() {
        return timeOfSession;
    }

    public void setTimeOfSession(String timeOfSession) {
        this.timeOfSession = timeOfSession;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
