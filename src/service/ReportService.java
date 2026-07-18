package service;

import model.Order;
import subscriber.OrderSubscriber;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class ReportService {

    public void publishOrders(List<Order> orders) {

        SubmissionPublisher<Order> publisher =
                new SubmissionPublisher<>();

        publisher.subscribe(new OrderSubscriber());

        for (Order order : orders) {

            System.out.println("Publishing -> " + order);

            publisher.submit(order);
        }

        publisher.close();
    }
}