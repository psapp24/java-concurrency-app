Part 5 – Multiple Publishers + Multiple Consumers

Until Part 4, we had:

Publisher-1
Publisher-2
Publisher-3
       |
       v
NotificationQueue
       |
       v
Consumer

Now we'll have:

                NotificationQueue
               +-----------------+
Publisher-1 -->|                 |--> Consumer-1
Publisher-2 -->|                 |--> Consumer-2
Publisher-3 -->|                 |--> Consumer-3
               +-----------------+

Now we have 6 threads working together.

Goal

Understand:

Multiple producers
Multiple consumers
Thread coordination
Why notify() can become problematic
Why notifyAll() exists

Important: We will still use notify() in this part. We want to observe its behavior before fixing it in the next part.

Execution Flow

Initially:

Consumer-1 ---> wait()

Consumer-2 ---> wait()

Consumer-3 ---> wait()

All consumers are sleeping.

Publisher-1 publishes a message.

Queue

Message-1

Then it executes:

notify();

Only one waiting consumer wakes up.

Example:

Consumer-2 wakes

↓

Consumes Message-1

↓

Consumer-1 still sleeping

Consumer-3 still sleeping

Next message:

Publisher-2

↓

notify()

↓

Consumer-1 wakes

Next:

Publisher-3

↓

notify()

↓

Consumer-3 wakes

Notice that which consumer wakes is chosen by the JVM. You cannot control it.

Thread State Diagram
Consumer-1 ---- WAITING

Consumer-2 ---- WAITING

Consumer-3 ---- WAITING

↓

Publisher calls notify()

↓

???

↓

One consumer wakes

↓

BLOCKED

↓

Gets monitor

↓

RUNNABLE

↓

Consumes message

The JVM decides which waiting thread receives the notification.

Does notify() always wake the oldest waiting thread?

No.

The JVM specification does not guarantee:

FIFO order ❌
Round-robin ❌
Fairness ❌

It simply wakes one arbitrary waiting thread.

For example:

Consumer-1 waiting 10 seconds

Consumer-2 waiting 5 seconds

Consumer-3 waiting 1 second

notify() may wake:

Consumer-3

or

Consumer-1

or

Consumer-2

There is no guarantee.

Is notify() wrong?

Not necessarily.

If:

One producer
One consumer

then:

notify()

↓

Perfect

But when there are multiple waiting threads, notify() can lead to inefficiencies because only one thread is awakened, even if multiple items become available in quick succession.

Interview Questions
Q1. How many threads does notify() wake?

Answer: Exactly one waiting thread.

Q2. Which thread does notify() wake?

Answer: An arbitrary waiting thread chosen by the JVM. There is no ordering or fairness guarantee.

Q3. Is notify() sufficient for multiple consumers?

Answer: It can work, but depending on the coordination logic, it may not always be the best choice. In more complex scenarios (especially with multiple producer and consumer conditions), notifyAll() is often safer.
