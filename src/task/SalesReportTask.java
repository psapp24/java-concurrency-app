package task;

import model.Order;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SalesReportTask extends RecursiveTask<Double> {

    private static final int THRESHOLD = 2000;

    private final List<Order> orders;
    private final int start;
    private final int end;

    public SalesReportTask(List<Order> orders, int start, int end) {
        this.orders = orders;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {

        System.out.println(Thread.currentThread().getName()
                + " processing "
                + start + " -> " + end);

        if ((end - start) <= THRESHOLD) {

            double total = 0;

            for (int i = start; i < end; i++) {
                total += orders.get(i).getAmount();
            }

            return total;
        }

        int mid = (start + end) / 2;

        SalesReportTask left =
                new SalesReportTask(orders, start, mid);

        SalesReportTask right =
                new SalesReportTask(orders, mid, end);

        left.fork();

        double rightResult = right.compute();

        double leftResult = left.join();

        return leftResult + rightResult;
    }
}