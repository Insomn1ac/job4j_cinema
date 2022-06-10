package ru.job4j.cinema.model;

public class Ticket {
    private int id;
    private int sessionId;
    private int posRow;
    private int cell;
    private int userId;

    public Ticket() {

    }

    public Ticket(int userId) {
        this.userId = userId;
    }

    public Ticket(int id, int sessionId, int posRow, int cell, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.posRow = posRow;
        this.cell = cell;
        this.userId = userId;
    }

    public Ticket(int sessionId, int posRow, int cell, int userId) {
        this.sessionId = sessionId;
        this.posRow = posRow;
        this.cell = cell;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
