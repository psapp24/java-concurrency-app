package queue;

import model.NotificationRequest;

public class NotificationQueue {

    private NotificationRequest request;

    public void add(NotificationRequest request) {

        System.out.println(Thread.currentThread().getName()
                + " -> Request added to Queue");

        this.request = request;
    }

    public NotificationRequest get() {

        return request;
    }
}