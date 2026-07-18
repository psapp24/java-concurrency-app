Part 8 – Graceful Shutdown (Poison Pill)

Until now, we've stopped consumers like this:

consumer.interrupt();

This is fine for learning, but in many real-world systems, consumers aren't stopped by interrupts. Instead, they finish processing all pending work and then exit cleanly.

Why is interrupt() not always enough?

Suppose the queue contains:

Queue

Message-1
Message-2
Message-3
Message-4

If we call:

consumer.interrupt();

The consumer may stop immediately, leaving messages unprocessed.

That's often undesirable.

Desired Behavior

We want this:

Publisher

↓

Publishes all messages

↓

Publishes one special message

↓

Consumer receives it

↓

Consumer understands

"No more work"

↓

Consumer exits

That special message is called a Poison Pill.

What is a Poison Pill?

It's simply a special object placed into the queue.

Normal messages:

Message-1

Message-2

Message-3

Special message:

POISON_PILL

The consumer treats it differently.

Step 1 – Update Notification

We'll create a constant representing the poison pill.

public class Notification {

    private final int id;
    private final String message;

    public static final Notification POISON_PILL =
            new Notification(-1, "POISON_PILL");

    public Notification(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Notification{id=" + id +
                ", message='" + message + "'}";
    }
}
Step 2 – Publisher

After publishing all normal messages:

@Override
public void run() {

    for (int i = 1; i <= 10; i++) {

        queue.publish(
                new Notification(i,
                        publisherName + "-Message-" + i));
    }

    queue.publish(Notification.POISON_PILL);

    System.out.println(publisherName + " Finished");
}
Step 3 – Consumer

Instead of:

while (!Thread.currentThread().isInterrupted())

We'll use:

while (true) {

    Notification notification = queue.consume();

    if (notification == Notification.POISON_PILL) {

        System.out.println(
                Thread.currentThread().getName()
                        + " received Poison Pill");

        break;
    }

    System.out.println(
            Thread.currentThread().getName()
                    + " processed "
                    + notification);
}

System.out.println(
        Thread.currentThread().getName()
                + " Finished");

No interrupt required.

Step 4 – Main

Old:

publisher.join();

consumer.interrupt();

consumer.join();

New:

publisher.join();

consumer.join();

System.out.println("Application Finished");

The consumer exits naturally after reading the poison pill.

Execution

Publisher:

Message-1

Message-2

Message-3

POISON_PILL

Queue:

Message-1

↓

Message-2

↓

Message-3

↓

POISON_PILL

Consumer:

Consumes Message-1

Consumes Message-2

Consumes Message-3

Consumes POISON_PILL

↓

Stops
Multiple Consumers

Suppose:

Consumer-1

Consumer-2

Consumer-3

How many poison pills do we need?

Answer: One for each consumer.

Queue:

Message-1

Message-2

POISON

POISON

POISON

Each consumer eventually receives one poison pill and exits.

If you insert only one poison pill:

POISON

then:

Consumer-1 exits

Consumer-2 waits forever

Consumer-3 waits forever
Real-world Uses

The poison pill pattern is common in:

Batch processing systems
Log processing
Message queues
ETL pipelines
Background worker services

Workers finish all queued tasks before shutting down.

Interview Questions
Q1. What is the Poison Pill pattern?

A special sentinel object placed in the queue to signal consumers that no more work will arrive and they should terminate gracefully.

Q2. Why use a Poison Pill instead of interrupt()?

Because it lets consumers finish processing all pending messages before exiting. An interrupt is a general thread cancellation mechanism and may stop processing before the queue is drained.

Q3. How many poison pills are needed?

One poison pill per consumer so that each consumer receives a shutdown signal.
