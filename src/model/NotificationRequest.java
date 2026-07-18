package model;

public class NotificationRequest {

    private final int id;
    private final String message;
    private final NotificationType type;

    public NotificationRequest(int id, String message, NotificationType type) {
        this.id = id;
        this.message = message;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}