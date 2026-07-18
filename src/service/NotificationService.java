package service;

import model.Notification;

import java.util.concurrent.BlockingQueue;

import java.util.concurrent.BlockingQueue;

public class NotificationService {

    private final BlockingQueue<Notification> queue;

    public NotificationService(
            BlockingQueue<Notification> queue) {

        this.queue = queue;
    }

    public void publish(Notification notification)
            throws InterruptedException {

        System.out.println(
                "Publishing -> " + notification);

        queue.put(notification);
    }
}