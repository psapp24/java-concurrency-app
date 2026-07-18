Part 6 – notify() vs notifyAll()
Current Situation

We have:

                NotificationQueue
               +-----------------+
Publisher-1 -->|                 |--> Consumer-1
Publisher-2 -->|                 |--> Consumer-2
Publisher-3 -->|                 |--> Consumer-3
               +-----------------+

When the queue is empty:

Consumer-1 ---> WAITING

Consumer-2 ---> WAITING

Consumer-3 ---> WAITING
Publisher adds one message
queue.offer(message);

notify();

Now what happens?

Consumer-1 ---> WAITING

Consumer-2 ---> RUNNABLE   ← JVM selected this one

Consumer-3 ---> WAITING

Only one consumer wakes up.

Suppose Publisher publishes 3 messages quickly
Message-1

Message-2

Message-3

With our code:

queue.offer(...);

notify();

Each publish() calls one notify(), so eventually different waiting consumers can wake and process the items.

What does notifyAll() do?

Replace

notify();

with

notifyAll();

Now:

Consumer-1 ---> WAITING

Consumer-2 ---> WAITING

Consumer-3 ---> WAITING

Publisher publishes one message.

notifyAll();

Result:

Consumer-1 wakes

Consumer-2 wakes

Consumer-3 wakes

But...

Can all three consume the same message?

No.

What actually happens?

All consumers wake up.

But they still need the monitor.

Consumer-1

↓

Gets monitor first

↓

poll()

↓

Gets Message-1

Queue becomes empty.

Now:

Consumer-2

↓

Gets monitor

↓

Queue Empty

↓

wait()

Consumer-3:

Gets monitor

↓

Queue Empty

↓

wait()

Only one consumer gets the message because access is synchronized.

Execution Timeline

With notifyAll():

Publisher

↓

offer()

↓

notifyAll()

↓

Consumer-1 wakes

Consumer-2 wakes

Consumer-3 wakes

↓

Consumer-1 gets monitor

↓

Consumes

↓

Consumer-2 gets monitor

↓

Queue empty

↓

wait()

↓

Consumer-3 gets monitor

↓

Queue empty

↓

wait()
So why use notifyAll()?

Imagine later we have a bounded queue.

Capacity = 5

Now there are two different waiting conditions:

Producers

Waiting because queue is FULL.

Producer

↓

wait()
Consumers

Waiting because queue is EMPTY.

Consumer

↓

wait()

Now both producers and consumers are waiting on the same monitor.

Example:

Producer-1 waiting

Producer-2 waiting

Consumer-1 waiting

Consumer-2 waiting

Now one consumer removes an item.

Which thread should wake?

Producer

because there is now free space.

But notify() may wake:

Consumer-2

Consumer-2 checks:

Queue empty?

Yes

↓

wait()

No progress was made.

This is why notifyAll() is often safer when multiple kinds of threads are waiting on the same monitor.



Interview Questions
Q1. Difference between notify() and notifyAll()?
notify()	notifyAll()
Wakes one waiting thread	Wakes all waiting threads
JVM chooses which thread	Every waiting thread is awakened
More efficient if only one waiter is needed	Safer when different types of threads are waiting
Q2. Which one should we use?

A good interview answer is:

If only one type of thread is waiting and waking a single thread is sufficient, notify() may be appropriate. If multiple threads or different waiting conditions share the same monitor, notifyAll() is generally safer because it prevents situations where the wrong type of thread is awakened and progress stalls.

Q3. Does notifyAll() let all threads run simultaneously?

No.

It wakes all waiting threads, but they still compete to acquire the monitor. Since the synchronized block allows only one thread at a time, they execute one after another.
