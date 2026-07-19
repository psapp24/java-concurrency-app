package controller;

import model.Order;
import service.OrderService;

public class OrderController {

    private final OrderService orderService =
            new OrderService();

    public void placeOrder(Order order) {

        System.out.println("Received Order : "
                + order.getOrderId());

        orderService.processOrder(order);

    }

}