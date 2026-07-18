package service;

import model.Product;

public class ProductService {

    public Product getProduct(String productId) {

        System.out.println(Thread.currentThread().getName()
                + " -> Calling Product Service");

        sleep(3000);

        System.out.println(Thread.currentThread().getName()
                + " -> Product Service Completed");

        return new Product(
                productId,
                "MacBook Pro",
                200000
        );
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}