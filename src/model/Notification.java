package model;

public class Notification {

    private final int id;
    private final String message;

    public static final Notification POISON_PILL =
            new Notification(-1, "POISON_PILL");

    public Notification(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getCustomerId() {
        return id;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
