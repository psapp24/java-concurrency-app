package consumer;

import model.Notification;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Notification> queue;
    private final String consumerName;

    public Consumer(BlockingQueue<Notification> queue,
                    String consumerName) {
        this.queue = queue;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {

        try {

            while (true) {

                Notification notification =
                        queue.take();

                if (notification == Notification.POISON_PILL) {

                    System.out.println(
                            consumerName
                                    + " stopping...");

                    break;
                }

                System.out.printf("[%s] Consumed %s%n",
                        consumerName,
                        notification);

                Thread.sleep(800);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}
