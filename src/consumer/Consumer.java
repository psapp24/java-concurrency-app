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

        while (true) {

            Notification notification = queue.consume();

            if (notification != null) {

                System.out.printf("[%s] Consumed <- %s (Queue Size=%d)%n",
                        Thread.currentThread().getName(),
                        notification,
                        queue.size());

            } else {

                System.out.println("Queue Empty...checking again...");
            }

            sleep(1000);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
