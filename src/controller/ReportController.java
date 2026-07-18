package controller;

import model.Order;
import service.ReportService;

import java.util.List;

public class ReportController {

    private final ReportService service =
            new ReportService();

    public void generate(List<Order> orders) {

        service.publishOrders(orders);
    }
}