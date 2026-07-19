package service;

import model.Order;

public class PackagingService {

    public void pack(Order order) {

        try {

            System.out.println(Thread.currentThread().getName()
                    + " Packaging Started");

            Thread.sleep(1500);

            System.out.println(Thread.currentThread().getName()
                    + " Packaging Completed");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

}