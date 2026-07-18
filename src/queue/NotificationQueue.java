package queue;

import model.Notification;

import java.util.LinkedList;
import java.util.Queue;

public class NotificationQueue {

    private final Queue<Notification> queue = new LinkedList<>();

    public synchronized void publish(Notification notification) {
        queue.offer(notification);
        System.out.println(
                Thread.currentThread().getName()
                        + " published " + notification);

        notify();
    }

    public synchronized Notification consume() {
        while (queue.isEmpty()) {

            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " waiting...");

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return null;
            }
        }
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}