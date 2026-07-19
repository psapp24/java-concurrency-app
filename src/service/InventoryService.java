package service;

import model.Order;

public class InventoryService {

    public void checkInventory(Order order) {
        try {
            System.out.println(Thread.currentThread().getName()
                    + " -> Checking Inventory");

            Thread.sleep(2000);

            System.out.println(Thread.currentThread().getName()
                    + " -> Inventory Verified");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}