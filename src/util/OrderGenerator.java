package util;

import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderGenerator {

    public static List<Order> generateOrders(int size) {

        Random random = new Random();

        List<Order> orders = new ArrayList<>(size);

        for (int i = 1; i <= size; i++) {
            orders.add(new Order(i, random.nextInt(1000) + 100));
        }

        return orders;
    }
}