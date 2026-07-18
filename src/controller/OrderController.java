package controller;

import model.Order;
import service.OrderService;

public class OrderController {

    private final OrderService orderService =
            new OrderService();

    public void placeOrder(Order order) {

        System.out.println(
                Thread.currentThread().getName()
                        + " -> Request Received"
        );

        Order processedOrder =
                orderService.process(order);

        System.out.println();

        System.out.println(
                "Final Order = "
                        + processedOrder
        );
    }
}