package controller;

import model.NotificationRequest;
import service.NotificationService;

public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    public void receive(NotificationRequest request) {

        System.out.println(Thread.currentThread().getName()
                + " -> Request Received");

        service.process(request);
    }
}