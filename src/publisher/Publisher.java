package publisher;

import model.Notification;
import queue.NotificationQueue;

public class Publisher implements Runnable {

    private final NotificationQueue queue;

    public Publisher(NotificationQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 20; i++) {

            Notification notification =
                    new Notification(i, "Message-" + i);

            queue.publish(notification);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Publisher Finished");
    }
}
