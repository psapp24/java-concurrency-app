package service;

import model.Discount;

public class DiscountService {

    public Discount getDiscount(String customerId) {

        System.out.println(Thread.currentThread().getName()
                + " -> Calling Discount Service");

        sleep(1000);

        System.out.println(Thread.currentThread().getName()
                + " -> Discount Service Completed");

        return new Discount(10);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}