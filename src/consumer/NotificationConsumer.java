package consumer;

import client.ExternalApiClient;
import model.NotificationRequest;
import queue.NotificationQueue;

public class NotificationConsumer extends Thread {

    private final NotificationQueue queue;
    private final ExternalApiClient externalApiClient;

    public NotificationConsumer(NotificationQueue queue,
                                ExternalApiClient externalApiClient) {

        this.queue = queue;
        this.externalApiClient = externalApiClient;
    }

    @Override
    public void run() {

        System.out.println(getName() + " -> Consumer Started");

        NotificationRequest request = queue.get();

        if (request == null) {
            System.out.println(getName() + " -> No Request Found");
            return;
        }

        System.out.println(getName()
                + " -> Processing Request : "
                + request.getId());

        externalApiClient.send(request);

        System.out.println(getName() + " -> Consumer Finished");
    }
}