package controller;

import model.Order;
import service.ReportService;

import java.util.List;

public class ReportController {

    private final ReportService reportService =
            new ReportService();

    public void generateReport(List<Order> orders) {

        System.out.println("\nGenerating Sales Report...\n");

        long start = System.currentTimeMillis();

        double total =
                reportService.calculateSales(orders);

        long end = System.currentTimeMillis();

        System.out.println("\n==============================");
        System.out.println("Total Sales : " + total);
        System.out.println("Time Taken  : " + (end - start) + " ms");
        System.out.println("==============================");
    }
}