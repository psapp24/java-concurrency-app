package publisher;

import model.NotificationRequest;
import queue.NotificationQueue;

public class NotificationPublisher {

    private final NotificationQueue queue;

    public NotificationPublisher(NotificationQueue queue) {
        this.queue = queue;
    }

    public void publish(NotificationRequest request) {

        System.out.println(Thread.currentThread().getName()
                + " -> Publishing Request");

        queue.add(request);
    }
}