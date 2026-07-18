package queue;
import model.Notification;

import java.util.LinkedList;
import java.util.Queue;

public class NotificationQueue {

    private final Queue<Notification> queue = new LinkedList<>();

    public void publish(Notification notification) {
        queue.offer(notification);
    }

    public Notification consume() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}