package model;

public class Notification {

    private final int id;
    private final String message;

    public Notification(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Notification{id=" + id +
                ", message='" + message + "'}";
    }
}
