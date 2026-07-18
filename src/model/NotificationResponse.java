package model;

public class NotificationResponse {

    private final boolean success;
    private final String message;

    public NotificationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}