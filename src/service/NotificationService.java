package service;

import model.NotificationRequest;
import publisher.NotificationPublisher;

public class NotificationService {

    private final ValidationService validationService;
    private final NotificationPublisher publisher;

    public NotificationService(ValidationService validationService,
                               NotificationPublisher publisher) {

        this.validationService = validationService;
        this.publisher = publisher;
    }

    public void process(NotificationRequest request) {

        if (!validationService.validate(request)) {
            throw new RuntimeException("Invalid Request");
        }

        System.out.println(Thread.currentThread().getName()
                + " -> Validation Successful");

        publisher.publish(request);
    }
}