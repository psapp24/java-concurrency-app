package service;

import model.Order;

import java.util.List;

public class ReportService {

    public double calculateSales(List<Order> orders) {

        return orders.parallelStream()
                .peek(order -> System.out.println(
                        Thread.currentThread().getName()
                                + " -> Order "
                                + order.getId()))
                .mapToDouble(Order::getAmount)
                .sum();
    }
}