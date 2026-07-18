package subscriber;

import model.Order;

import java.util.concurrent.Flow;

public class OrderSubscriber implements Flow.Subscriber<Order> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

        this.subscription = subscription;

        System.out.println("Subscribed");

        // Ask for the first item
        subscription.request(1);
    }

    @Override
    public void onNext(Order item) {

        System.out.println(Thread.currentThread().getName()
                + " received -> " + item);

        try {
            Thread.sleep(1000);   // Simulate slow processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Ask for the next item only after processing
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error : " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("All Orders Processed");
    }
}