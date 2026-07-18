package service;

import model.NotificationRequest;

public class ValidationService {

    public boolean validate(NotificationRequest request) {

        System.out.println(Thread.currentThread().getName()
                + " -> Validating Request");

        return request.getMessage() != null
                && !request.getMessage().isBlank();
    }
}