package service;

import model.Order;

import java.util.concurrent.*;

public class OrderService {

    private final InventoryService inventoryService = new InventoryService();
    private final CustomerService customerService = new CustomerService();
    private final PaymentService paymentService = new PaymentService();
    private final PackagingService packagingService = new PackagingService();
    private final RabbitPublisher rabbitPublisher = new RabbitPublisher();

    private final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public void processOrder(Order order) {

        CyclicBarrier barrier =
                new CyclicBarrier(
                        4,
                        () -> System.out.println(
                                "\n******** Invoice Generated ********\n"));

        submitInventory(order, barrier);
        submitCustomer(order, barrier);
        submitPayment(order, barrier);
        submitPackaging(order, barrier);

        executor.shutdown();

        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        rabbitPublisher.publish(order);
    }

    private void submitInventory(Order order,
                                 CyclicBarrier barrier) {

        executor.submit(() -> {

            inventoryService.checkInventory(order);

            waitAtBarrier("Inventory", barrier);

            System.out.println("Inventory Team Continued");

        });

    }

    private void submitCustomer(Order order,
                                CyclicBarrier barrier) {

        executor.submit(() -> {

            customerService.validateCustomer(order);

            waitAtBarrier("Customer", barrier);

            System.out.println("Customer Team Continued");

        });

    }

    private void submitPayment(Order order,
                               CyclicBarrier barrier) {

        executor.submit(() -> {

            paymentService.processPayment(order);

            waitAtBarrier("Payment", barrier);

            System.out.println("Payment Team Continued");

        });

    }

    private void submitPackaging(Order order,
                                 CyclicBarrier barrier) {

        executor.submit(() -> {

            packagingService.pack(order);

            waitAtBarrier("Packaging", barrier);

            System.out.println("Packaging Team Continued");

        });

    }

    private void waitAtBarrier(String team,
                               CyclicBarrier barrier) {

        try {

            System.out.println(team + " waiting...");

            barrier.await();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (BrokenBarrierException e) {

            e.printStackTrace();

        }

    }

}