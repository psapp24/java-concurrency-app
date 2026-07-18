package queue;

import model.Notification;

import java.util.LinkedList;
import java.util.Queue;

public class NotificationQueue {

    private final Queue<Notification> queue = new LinkedList<>();

    public synchronized void publish(Notification notification) {
        queue.offer(notification);

        System.out.printf("[%s] Published -> %s | Queue Size=%d%n",
                Thread.currentThread().getName(),
                notification,
                queue.size());

        notifyAll();
    }

    public synchronized Notification consume() {
        while (queue.isEmpty()) {

            try {
                System.out.printf("[%s] Waiting...%n",
                        Thread.currentThread().getName());

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return null;
            }
        }
        return queue.poll();
    }
}