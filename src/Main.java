import controller.OrderController;
import model.Order;

public class Main {

    public static void main(String[] args) {

        Order order =
                new Order(
                        "O101",
                        "C101",
                        "P101"
                );

        OrderController controller =
                new OrderController();

        controller.placeOrder(order);
    }
}