package consumer;

import model.Notification;
import queue.NotificationQueue;

public class Consumer implements Runnable {

    private final NotificationQueue queue;
    private final String consumerName;

    public Consumer(NotificationQueue queue, String consumerName) {
        this.queue = queue;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {

        while (true) {

            Notification notification = queue.consume();

            if (notification == Notification.POISON_PILL) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " received Poison Pill");

                break;
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " processed "
                            + notification);
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " Finished");
    }
}
