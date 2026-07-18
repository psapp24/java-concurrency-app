package client;

import model.Customer;
import model.Notification;
import model.Template;

public class ExternalNotificationClient {

    public void send(Customer customer,
                     Template template,
                     Notification notification) {

        sleep(2000);

        System.out.println(
                Thread.currentThread().getName()
                        + " -> Sending Notification");

        System.out.println(
                "Customer : " + customer.getName());

        System.out.println(
                "Template : " + template.getTemplate());

        System.out.println(
                "Message  : " + notification.getMessage());

        System.out.println(
                "Notification Sent Successfully\n");
    }

    private void sleep(long ms) {

        try {

            Thread.sleep(ms);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}