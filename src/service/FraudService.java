package service;

import model.Order;

public class FraudService {

    public void verifyFraud(Order order) {

        try {

            System.out.println(Thread.currentThread().getName()
                    + " -> Fraud Check Started");

            Thread.sleep(1500);

            System.out.println(Thread.currentThread().getName()
                    + " -> Fraud Check Passed");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}