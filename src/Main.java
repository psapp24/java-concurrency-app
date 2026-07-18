import consumer.Consumer;
import publisher.Publisher;
import queue.NotificationQueue;

public class Main {

    public static void main(String[] args) throws Exception {

        NotificationQueue queue = new NotificationQueue();

        Thread p1 = new Thread(new Publisher(queue, "Publisher-1"));
        Thread p2 = new Thread(new Publisher(queue, "Publisher-2"));
        Thread p3 = new Thread(new Publisher(queue, "Publisher-3"));

        Thread c1 = new Thread(new Consumer(queue, "Consumer-1"));
        Thread c2 = new Thread(new Consumer(queue, "Consumer-2"));
        Thread c3 = new Thread(new Consumer(queue, "Consumer-3"));

        c1.start();
        c2.start();
        c3.start();

        p1.start();
        p2.start();
        p3.start();

        p1.join();
        p2.join();
        p3.join();

        Thread.sleep(5000);

        c1.interrupt();
        c2.interrupt();
        c3.interrupt();

        c1.join();
        c2.join();
        c3.join();

        System.out.println("Application Finished");
    }
}