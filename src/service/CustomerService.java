package service;

import model.Customer;

public class CustomerService {

    public Customer getCustomer(int customerId) {

        sleep(2000);

        System.out.println(
                Thread.currentThread().getName()
                        + " -> Customer Service");

        return new Customer(
                customerId,
                "Prakash");
    }

    private void sleep(long ms) {

        try {

            Thread.sleep(ms);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}
