import controller.ReportController;
import model.Order;
import util.OrderGenerator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Order> orders =
                OrderGenerator.generateOrders(10000);

        ReportController controller =
                new ReportController();

        controller.generateReport(orders);
    }
}