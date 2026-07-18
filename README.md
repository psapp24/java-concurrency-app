Part 7 – Bounded Queue (Queue Capacity)

Until now our queue could grow forever.

Publisher
     |
     v
LinkedList (Unlimited)
     |
     v
Consumer

Imagine the publisher is much faster than the consumer.

Producer : 1000 messages/sec

Consumer : 10 messages/sec

After one minute:

Queue Size

10

100

1000

10000

100000

...

Eventually you'll get:

OutOfMemoryError

This is exactly why real messaging systems (Kafka, RabbitMQ, ActiveMQ, etc.) support backpressure and why Java provides bounded queues.

Goal

Let's limit the queue size.

Suppose:

Capacity = 5

Our queue should behave like this:

Capacity = 5

[ ]
[ ]
[ ]
[ ]
[ ]
New Rules
Rule 1

If queue is empty

Consumer

↓

wait()
Rule 2

If queue is full

Producer

↓

wait()
Rule 3

Consumer removes one item

notifyAll()

so producers can continue.

Rule 4

Producer adds one item

notifyAll()

so consumers can continue.

Architecture
                   Capacity = 5

Publisher-1
Publisher-2
Publisher-3
      |
      v
+----------------------+
| NotificationQueue    |
| LinkedList           |
| Max Size = 5         |
+----------------------+
      |
      v
Consumer-1
Consumer-2
Consumer-3


What happens?

Suppose capacity = 5.

Initially

Queue

[]

Publisher adds

1
2
3
4
5

Queue becomes

[1][2][3][4][5]

Now Publisher-2 wants to add Message-6.

Queue Full

↓

wait()

Producer sleeps.

Consumer removes one item.

Queue

[2][3][4][5]

Consumer executes

notifyAll();

Publisher wakes.

Publisher adds

6

Queue becomes

[2][3][4][5][6]
Thread Flow
Producer

↓

Queue Full

↓

WAITING

↓

Consumer removes item

↓

notifyAll()

↓

Producer wakes

↓

Adds item

↓

Running
Why do we call notifyAll() in both methods?
In publish()

After adding an item:

Queue was empty

↓

Consumer may be waiting

↓

Wake consumer
In consume()

After removing an item:

Queue was full

↓

Producer may be waiting

↓

Wake producer
Why not use notify()?

Imagine:

Capacity = 5

Queue Full

Producer-1 waiting

Producer-2 waiting

Consumer-1 waiting

Consumer-2 waiting

Consumer removes one item.

There is now one free slot.

Who should wake?

Producer

But notify() might wake:

Consumer-2

Consumer checks:

Queue empty?

No


In this particular example it may still make progress by consuming another item. But in more complex situations with mixed waiting conditions, notify() can repeatedly wake threads that cannot make the desired progress, leading to inefficient scheduling or even stalls. notifyAll() wakes everyone so that the thread whose condition has become true can acquire the monitor and continue.

Why this looks like BlockingQueue

If you've used:

BlockingQueue<String> queue =
        new ArrayBlockingQueue<>(5);

and

queue.put(message);

Internally it behaves conceptually like:

while(queueIsFull){

    wait();
}

add();

notifyAll();

Similarly,

queue.take();

behaves conceptually like:

while(queueIsEmpty){

    wait();
}

remove();

notifyAll();

The JDK implementation uses ReentrantLock and separate Condition objects (notFull and notEmpty) rather than synchronized and wait()/notifyAll(), but the coordination idea is the same.

Interview Questions
Q1. Why do we use while instead of if?

Because after waking up, another thread may have already changed the queue state. We must re-check the condition before proceeding.

Q2. Why does the producer wait?

Because the queue has reached its maximum capacity and accepting more items could exhaust memory.

Q3. Why does the consumer wait?

Because there is no work available to process.

Q4. What real Java class behaves like this?

A good answer is:

ArrayBlockingQueue
LinkedBlockingQueue

They implement the same producer-consumer coordination pattern, but with more advanced synchronization mechanisms.
