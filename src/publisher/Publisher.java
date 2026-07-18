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

            System.out.printf("[%s] Published -> %s (Queue Size=%d)%n",
                    Thread.currentThread().getName(),
                    notification,
                    queue.size());

            sleep(500);
        }

        System.out.println("Publisher Finished");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
