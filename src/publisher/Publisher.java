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

            queue.publish(
                    new Notification(i,
                            publisherName + "-Message-" + i));
        }

        queue.publish(Notification.POISON_PILL);

        System.out.println(publisherName + " Finished");
    }
}