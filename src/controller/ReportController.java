package controller;

import model.Order;
import service.ReportService;

import java.util.List;

public class ReportController {

    private final ReportService reportService =
            new ReportService();

    public void generateReport(List<Order> orders) {

        long start = System.currentTimeMillis();

        double total =
                reportService.calculateSales(orders);

        long end = System.currentTimeMillis();

        System.out.println("\nTotal Sales : " + total);
        System.out.println("Time Taken  : " + (end - start) + " ms");
    }
}