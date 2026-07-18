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

        while (!Thread.currentThread().isInterrupted()) {

            Notification notification = queue.consume();

            if (notification != null) {

                System.out.printf("[%s] Consumed <- %s%n",
                        consumerName,
                        notification);

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println(consumerName + " Finished");
    }
}
