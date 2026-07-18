package consumer;

import model.Notification;
import queue.NotificationQueue;

public class Consumer implements Runnable {

    private final NotificationQueue queue;

    public Consumer(NotificationQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            Notification notification = queue.consume();

            if (notification != null) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " consumed "
                                + notification);
            }
        }
        System.out.println("Consumer Finished");
    }
}
