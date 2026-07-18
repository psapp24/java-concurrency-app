package publisher;

import model.Notification;

import java.util.concurrent.BlockingQueue;

public class Publisher implements Runnable {

    private final BlockingQueue<Notification> queue;
    private final String publisherName;

    public Publisher(BlockingQueue<Notification> queue,
                     String publisherName) {
        this.queue = queue;
        this.publisherName = publisherName;
    }

    @Override
    public void run() {

        try {

            for (int i = 1; i <= 10; i++) {

                Notification notification =
                        new Notification(
                                i,
                                publisherName + "-Message-" + i);

                queue.put(notification);

                System.out.printf("[%s] Published %s%n",
                        publisherName,
                        notification);

                Thread.sleep(500);
            }

            queue.put(Notification.POISON_PILL);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}