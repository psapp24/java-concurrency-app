package service;

import model.Payment;

import java.util.UUID;

public class PaymentService {

    public Payment makePayment(double amount) {

        System.out.println(Thread.currentThread().getName()
                + " -> Payment Started : " + amount);

        sleep(2000);

        System.out.println(Thread.currentThread().getName()
                + " -> Payment Completed");

        return new Payment(
                true,
                UUID.randomUUID().toString()
        );
    }

    public Payment makeFailedPayment(double amount) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " -> Payment Started");

            sleep(2000);

            throw new RuntimeException("Payment Gateway Down");

    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}