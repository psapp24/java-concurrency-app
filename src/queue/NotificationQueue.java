package queue;

import model.Notification;

import java.util.LinkedList;
import java.util.Queue;

import java.util.LinkedList;
import java.util.Queue;

public class NotificationQueue {

    private final Queue<Notification> queue = new LinkedList<>();

    private final int capacity;

    public NotificationQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void publish(Notification notification) {

        while (queue.size() == capacity) {

            try {

                System.out.printf("[%s] Queue FULL. Waiting...%n",
                        Thread.currentThread().getName());

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return;
            }
        }

        queue.offer(notification);

        System.out.printf("[%s] Published %s | Queue Size=%d%n",
                Thread.currentThread().getName(),
                notification,
                queue.size());

        notifyAll();
    }

    public synchronized Notification consume() {

        while (queue.isEmpty()) {

            try {

                System.out.printf("[%s] Queue EMPTY. Waiting...%n",
                        Thread.currentThread().getName());

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return null;
            }
        }

        Notification notification = queue.poll();

        System.out.printf("[%s] Removed %s | Queue Size=%d%n",
                Thread.currentThread().getName(),
                notification,
                queue.size());

        notifyAll();

        return notification;
    }
}