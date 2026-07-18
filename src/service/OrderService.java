package service;

import model.Customer;
import model.Discount;
import model.Order;
import model.Payment;
import model.Product;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderService {

    private final CustomerService customerService =
            new CustomerService();

    private final ProductService productService =
            new ProductService();

    private final DiscountService discountService =
            new DiscountService();

    private final PaymentService paymentService =
            new PaymentService();

    private final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public Order processOld(Order order) {

        CompletableFuture<Customer> customerFuture =
                CompletableFuture
                        .supplyAsync(
                                () -> customerService.getCustomer(
                                        order.getCustomerId()),
                                executor
                        )
                        .thenApply(customer -> {

                            System.out.println(
                                    Thread.currentThread().getName()
                                            + " -> thenApply() Executing");

                            return new Customer(
                                    customer.getCustomerId(),
                                    customer.getName().toUpperCase()
                            );
                        });

        Customer customer = customerFuture.join();

        order.setCustomer(customer);

        return order;
    }

    public Order process(Order order) {

        CompletableFuture<Payment> paymentFuture =
                CompletableFuture

                        .supplyAsync(
                                () -> productService.getProduct(
                                        order.getProductId()),
                                executor)

                        .thenCompose(product -> {

                            System.out.println(
                                    Thread.currentThread().getName()
                                            + " -> thenCompose()");

                            order.setProduct(product);

                            return CompletableFuture.supplyAsync(
                                    () -> paymentService.makePayment(
                                            product.getPrice()),
                                    executor);

                        });

        Payment payment = paymentFuture.join();

        order.setPayment(payment);

        return order;
    }

    public Order processUsingThenCombine(Order order) {

        CompletableFuture<Product> productFuture =
                CompletableFuture.supplyAsync(
                        () -> productService.getProduct(
                                order.getProductId()),
                        executor);

        CompletableFuture<Discount> discountFuture =
                CompletableFuture.supplyAsync(
                        () -> discountService.getDiscount(
                                order.getCustomerId()),
                        executor);

        CompletableFuture<Double> finalPriceFuture =
                productFuture.thenCombine(
                        discountFuture,

                        (product, discount) -> {

                            System.out.println(
                                    Thread.currentThread().getName()
                                            + " -> thenCombine()");

                            order.setProduct(product);
                            order.setDiscount(discount);

                            return product.getPrice()
                                    - (product.getPrice()
                                    * discount.getPercentage() / 100);
                        });

        Double finalPrice = finalPriceFuture.join();

        order.setFinalPrice(finalPrice);

        return order;
    }

    public Order processUsingExceptionally(Order order) {

        CompletableFuture<Payment> paymentFuture =

                CompletableFuture

                        .supplyAsync(

                                () -> paymentService.makePayment(

                                        1000),

                                executor)

                        .exceptionally(ex -> {

                            System.out.println(

                                    Thread.currentThread().getName()

                                            + " -> Recovering from : "

                                            + ex.getMessage());

                            return new Payment(

                                    false,

                                    "PAYMENT_FAILED");

                        });

        Payment payment = paymentFuture.join();

        order.setPayment(payment);

        return order;
    }
}