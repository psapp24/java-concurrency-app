package controller;

import model.Notification;
import service.NotificationService;

public class NotificationController {

    private final NotificationService service;

    public NotificationController(
            NotificationService service) {

        this.service = service;
    }

    public void sendNotification(int customerId,
                                 String message)
            throws InterruptedException {

        Notification notification =
                new Notification(customerId, message);

        service.publish(notification);

        System.out.println(
                "HTTP Response -> 202 ACCEPTED\n");
    }
}