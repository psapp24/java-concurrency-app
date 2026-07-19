package service;

import model.Order;

public class RabbitPublisher {

    public void publish(Order order) {

        System.out.println();

        System.out.println("************************************************");

        System.out.println("Publishing Order "
                + order.getOrderId()
                + " to RabbitMQ");

        System.out.println("************************************************");
    }

}