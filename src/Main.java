import controller.OrderController;
import model.Order;

public class Main {

    public static void main(String[] args) {

        OrderController controller =
                new OrderController();

        controller.placeOrder(new Order(101));

    }

}