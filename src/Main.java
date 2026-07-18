import consumer.NotificationConsumer;
import controller.NotificationController;
import model.Notification;
import service.NotificationService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args)
            throws Exception {

        BlockingQueue<Notification> queue =
                new ArrayBlockingQueue<>(10);

        NotificationService service =
                new NotificationService(queue);

        NotificationController controller =
                new NotificationController(service);

        Thread consumer =
                new Thread(
                        new NotificationConsumer(queue),
                        "Notification-Consumer");

        consumer.start();

        controller.sendNotification(
                101,
                "Welcome");

        controller.sendNotification(
                102,
                "OTP Generated");

        controller.sendNotification(
                103,
                "Order Placed");

        Thread.sleep(15000);

        queue.put(Notification.POISON_PILL);

        consumer.join();

        System.out.println("\nApplication Finished");
    }
}