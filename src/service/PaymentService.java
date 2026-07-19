package service;

import model.Order;

public class PaymentService {

    public void processPayment(Order order) {

        try {

            System.out.println(Thread.currentThread().getName()
                    + " Processing Payment");

            Thread.sleep(2500);

            System.out.println(Thread.currentThread().getName()
                    + " Payment Completed");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

}