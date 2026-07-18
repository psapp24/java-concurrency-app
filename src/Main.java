import client.ExternalApiClient;
import consumer.NotificationConsumer;
import controller.NotificationController;
import model.NotificationRequest;
import model.NotificationType;
import publisher.NotificationPublisher;
import queue.NotificationQueue;
import service.NotificationService;
import service.ValidationService;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        NotificationQueue queue = new NotificationQueue();

        NotificationPublisher publisher =
                new NotificationPublisher(queue);

        ValidationService validationService =
                new ValidationService();

        NotificationService service =
                new NotificationService(validationService,
                        publisher);

        NotificationController controller =
                new NotificationController(service);

        NotificationConsumer consumer =
                new NotificationConsumer(
                        queue,
                        new ExternalApiClient());

        consumer.setName("Consumer-1");

        NotificationRequest request =
                new NotificationRequest(
                        1,
                        "Welcome to Java Concurrency Lab",
                        NotificationType.EMAIL);

        System.out.println("================================");
        System.out.println("1:Consumer State : "
                + consumer.getState());
        System.out.println("================================");

        controller.receive(request);

        System.out.println("================================");
        System.out.println("2:Consumer State : "
                + consumer.getState());
        System.out.println("================================");

        consumer.start();

        //Thread.sleep(100);
        System.out.println("================================");
        System.out.println("3:Consumer State : "
                + consumer.getState());
        System.out.println("================================");

        consumer.join();

        System.out.println("================================");
        System.out.println("4:Consumer State : "
                + consumer.getState());
        System.out.println("================================");
    }
}