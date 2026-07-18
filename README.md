Part 4 – Multiple Publishers, One Consumer

Until now:

Publisher
      |
      v
NotificationQueue
      |
      v
Consumer

Only one thread was producing data.

Now we'll have:

Publisher-1
             \
              \
Publisher-2 -----> NotificationQueue -----> Consumer
              /
             /
Publisher-3

Now multiple threads are trying to access the same shared queue simultaneously.

What changes?

Actually...

Almost nothing!

That's the beauty of using synchronized.

Our NotificationQueue already protects the queue.

public synchronized void publish(Notification notification)

Only one publisher can execute this method at a time.

Sample Output
Publisher-1 published Notification{id=1, message='Publisher-1 -> Message-1'}

Consumer consumed Notification{id=1, message='Publisher-1 -> Message-1'}

Publisher-3 published Notification{id=1, message='Publisher-3 -> Message-1'}

Publisher-2 published Notification{id=1, message='Publisher-2 -> Message-1'}

Consumer consumed Notification{id=1, message='Publisher-3 -> Message-1'}

Consumer consumed Notification{id=1, message='Publisher-2 -> Message-1'}

Publisher-1 published Notification{id=2, message='Publisher-1 -> Message-2'}

Publisher-2 published Notification{id=2, message='Publisher-2 -> Message-2'}

Publisher-3 published Notification{id=2, message='Publisher-3 -> Message-2'}

Notice:

Messages from different publishers are interleaved.
The queue preserves FIFO order based on the order in which publish() completes.
No data is lost.
What's happening internally?

Imagine all three publishers reach publish() at the same time.

                 NotificationQueue

Publisher-1  --------\
Publisher-2  ---------+---- publish()
Publisher-3  --------/

But publish() is synchronized.

Only one thread gets the monitor.

Suppose:

Publisher-2 gets monitor

Publisher-1 waits

Publisher-3 waits

Execution becomes:

Publisher-2

Acquire Monitor

↓

offer()

↓

notify()

↓

Release Monitor

↓

Publisher-1 acquires monitor

↓

offer()

↓

notify()

↓

Release Monitor

↓

Publisher-3 acquires monitor

Even though all publishers are running concurrently, access to the shared queue is serialized.

Why don't we need additional synchronization?

Because both methods are synchronized:

public synchronized void publish(...)
public synchronized Notification consume(...)

The monitor belongs to the NotificationQueue object.

So whether there are:

1 publisher
5 publishers
20 publishers

only one thread at a time can modify the queue.

Thread View
                NotificationQueue Monitor
                       |
------------------------------------------------
Publisher-1     WAITING
Publisher-2     RUNNING
Publisher-3     WAITING
Consumer        WAITING (wait())

After Publisher-2 calls notify():

Publisher-2 releases monitor

↓

Consumer becomes BLOCKED
(wait() returns, but it must reacquire the monitor)

↓

Consumer acquires monitor

↓

Consumes message

↓

Queue empty?

↓

wait()
Interview Questions
Q1. Can multiple publishers execute publish() simultaneously?

Answer: No. Since publish() is synchronized, only one thread can hold the NotificationQueue monitor at a time.

Q2. Does synchronized reduce concurrency?

Answer: It reduces parallel execution of the critical section, but only for the shared resource. The rest of each publisher's work (creating notifications, sleeping, logging outside synchronized methods, etc.) can still run concurrently.

Q3. Is the queue safe with multiple producers?

Answer: Yes. Because all access to the LinkedList happens through synchronized methods on the same NotificationQueue instance.
