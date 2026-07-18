package publisher;

import model.Notification;
import queue.NotificationQueue;

public class Publisher implements Runnable {

    private final NotificationQueue queue;
    private final String publisherName;

    public Publisher(NotificationQueue queue, String publisherName) {
        this.queue = queue;
        this.publisherName = publisherName;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 10; i++) {

            Notification notification = new Notification(
                    i,
                    publisherName + " -> Message-" + i
            );

            queue.publish(notification);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(publisherName + " Finished");
    }
}