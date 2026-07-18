import consumer.Consumer;
import publisher.Publisher;
import queue.NotificationQueue;

public class Main {

    public static void main(String[] args) throws Exception {
        NotificationQueue queue = new NotificationQueue();

        Thread publisher1 =
                new Thread(new Publisher(queue, "Publisher-1"));

        Thread publisher2 =
                new Thread(new Publisher(queue, "Publisher-2"));

        Thread publisher3 =
                new Thread(new Publisher(queue, "Publisher-3"));

        Thread consumer =
                new Thread(new Consumer(queue), "Consumer");

        consumer.start();

        publisher1.start();
        publisher2.start();
        publisher3.start();

        publisher1.join();
        publisher2.join();
        publisher3.join();

        Thread.sleep(3000);

        consumer.interrupt();
        consumer.join();

        System.out.println("Application Finished");
    }
}