package service;

import model.Order;

public class CustomerService {

    public void validateCustomer(Order order) {

        try {

            System.out.println(Thread.currentThread().getName()
                    + " -> Validating Customer");

            Thread.sleep(1000);

            System.out.println(Thread.currentThread().getName()
                    + " -> Customer Verified");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}