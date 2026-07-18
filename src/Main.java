import consumer.Consumer;
import publisher.Publisher;
import queue.NotificationQueue;

public class Main {

    public static void main(String[] args) throws Exception {
        NotificationQueue queue = new NotificationQueue();

        Thread publisher =
                new Thread(new Publisher(queue), "Publisher");

        Thread consumer =
                new Thread(new Consumer(queue), "Consumer");

        publisher.start();
        consumer.start();

        publisher.join();

        Thread.sleep(5000);

        consumer.interrupt();

        System.out.println("Main Finished");
    }
}