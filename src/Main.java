import controller.ReportController;
import model.Order;

import java.util.List;

public class Main {

    public static void main(String[] args)
            throws Exception {

        List<Order> orders = List.of(
                new Order(1, 500),
                new Order(2, 600),
                new Order(3, 700),
                new Order(4, 800),
                new Order(5, 900)
        );

        ReportController controller =
                new ReportController();

        controller.generate(orders);

        Thread.sleep(7000);
    }
}