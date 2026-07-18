package client;

import model.NotificationRequest;
import model.NotificationResponse;

public class ExternalApiClient {

    public NotificationResponse send(NotificationRequest request) {

        System.out.println(Thread.currentThread().getName()
                + " -> Calling External API...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()
                + " -> External API completed");

        return new NotificationResponse(
                true,
                "Notification Sent Successfully"
        );
    }
}