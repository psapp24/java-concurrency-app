import consumer.Consumer;
import model.Notification;
import publisher.Publisher;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        BlockingQueue<Notification> queue =
                new ArrayBlockingQueue<>(5);

        Thread publisher =
                new Thread(
                        new Publisher(queue,
                                "Publisher"));

        Thread consumer =
                new Thread(
                        new Consumer(queue,
                                "Consumer"));

        consumer.start();
        publisher.start();

        publisher.join();
        consumer.join();

        System.out.println("Application Finished");
    }
}