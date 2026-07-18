package service;

import model.Order;
import task.SalesReportTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ReportService {

    private final ForkJoinPool pool = new ForkJoinPool();

    public double calculateSales(List<Order> orders) {

        SalesReportTask task =
                new SalesReportTask(orders, 0, orders.size());

        return pool.invoke(task);
    }
}