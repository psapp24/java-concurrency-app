package consumer;

import client.ExternalNotificationClient;
import model.Customer;
import model.Notification;
import model.Template;
import service.CustomerService;
import service.TemplateService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class NotificationConsumer implements Runnable {

    private final BlockingQueue<Notification> queue;

    private final CustomerService customerService =
            new CustomerService();

    private final TemplateService templateService =
            new TemplateService();

    private final ExternalNotificationClient client =
            new ExternalNotificationClient();

    public NotificationConsumer(
            BlockingQueue<Notification> queue) {

        this.queue = queue;
    }

    @Override
    public void run() {

        while (true) {

            try {

                Notification notification = queue.take();

                if (notification == Notification.POISON_PILL) {

                    System.out.println("Consumer Stopped");
                    break;
                }

                System.out.println(
                        "\nConsumer received -> "
                                + notification);

                CompletableFuture<Customer> customerFuture =
                        CompletableFuture.supplyAsync(
                                () -> customerService.getCustomer(
                                        notification.getCustomerId()));

                CompletableFuture<Template> templateFuture =
                        CompletableFuture.supplyAsync(
                                templateService::getTemplate);

                customerFuture
                        .thenCombine(
                                templateFuture,
                                (customer, template) -> {

                                    client.send(
                                            customer,
                                            template,
                                            notification);

                                    return null;
                                })
                        .join();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                break;
            }
        }
    }
}