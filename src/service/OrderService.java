package service;

import model.Order;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderService {

    private final InventoryService inventoryService = new InventoryService();
    private final CustomerService customerService = new CustomerService();
    private final DiscountService discountService = new DiscountService();
    private final FraudService fraudService = new FraudService();
    private final RabbitPublisher rabbitPublisher = new RabbitPublisher();

    private final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public void processOrder(Order order) {

        CountDownLatch latch = new CountDownLatch(4);

        System.out.println("Starting Validation...\n");

        executor.submit(() -> {

            inventoryService.checkInventory(order);

            latch.countDown();

            System.out.println("Latch Count : " + latch.getCount());

        });

        executor.submit(() -> {

            customerService.validateCustomer(order);

            latch.countDown();

            System.out.println("Latch Count : " + latch.getCount());

        });

        executor.submit(() -> {

            discountService.calculateDiscount(order);

            latch.countDown();

            System.out.println("Latch Count : " + latch.getCount());

        });

        executor.submit(() -> {

            fraudService.verifyFraud(order);

            latch.countDown();

            System.out.println("Latch Count : " + latch.getCount());

        });

        try {

            System.out.println("\nWaiting for all validations...\n");

            latch.await();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        rabbitPublisher.publish(order);

        executor.shutdown();
    }
}