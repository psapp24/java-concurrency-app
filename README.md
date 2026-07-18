Part 9 – BlockingQueue

After building everything ourselves, we'll now replace our custom implementation with Java's built-in solution.

You'll immediately appreciate how much work BlockingQueue saves.

Let's Compare
Our implementation (Parts 1–8)
Publisher
      |
      v
NotificationQueue
    |
    |-- LinkedList
    |-- synchronized
    |-- wait()
    |-- notifyAll()
    |-- capacity check
    |-- graceful shutdown
    |
    v
Consumer

We wrote around 100+ lines just for queue management.

Java's implementation
Publisher
      |
      v
BlockingQueue
      |
      v
Consumer

That's it.

What does BlockingQueue provide?

Everything we implemented manually:

✅ Thread safety

✅ Blocking producers

✅ Blocking consumers

✅ Capacity management

✅ Memory visibility

✅ Internal locking

✅ Condition signaling

No wait().

No notify().

No synchronized.

Project Structure
part9-blocking-queue
│
├── Notification.java
├── Publisher.java
├── Consumer.java
└── Main.java


Wait...

Where did all these go?

synchronized

Gone.

wait();

Gone.

notifyAll();

Gone.

while(queue.isEmpty())

Gone.

while(queue.size()==capacity)

Gone.

Java handles everything internally.

What does put() do?

Conceptually, it's similar to:

while(queueIsFull){

    wait();
}

queue.add(item);

notifyAll();

You don't see that code because it's inside the JDK.

What does take() do?

Conceptually:

while(queueIsEmpty){

    wait();
}

Notification n = queue.remove();

notifyAll();

return n;

Again, handled internally.

Internal Comparison

Our code:

public synchronized void publish(Notification notification){

    while(queue.size()==capacity){

        wait();
    }

    queue.offer(notification);

    notifyAll();
}

BlockingQueue:

queue.put(notification);

Same behavior.

Much cleaner API.

Which implementation does BlockingQueue use?

Actually...

Not synchronized.

It uses

ReentrantLock

plus

Condition

instead of

wait()

notify()

We'll study those later.

Which BlockingQueue implementations exist?
Implementation	Bounded?	Notes
ArrayBlockingQueue	✅ Yes	Fixed-size array, bounded
LinkedBlockingQueue	✅ Usually	Linked nodes, optionally bounded
PriorityBlockingQueue	❌ No	Orders by priority instead of FIFO
DelayQueue	❌ No	Elements become available after a delay
SynchronousQueue	N/A	No storage; direct handoff between producer and consumer

For our demo, ArrayBlockingQueue is the best fit because it behaves almost exactly like the bounded queue we implemented ourselves.

Interview Questions
Q1. Why use BlockingQueue instead of LinkedList?

Because it provides built-in thread safety, blocking behavior, and capacity management, eliminating the need to manually write synchronization code.

Q2. What is the difference between put() and offer()?
put()	offer()
Waits if the queue is full	Returns immediately (false if full)
Q3. What is the difference between take() and poll()?
take()	poll()
Waits if the queue is empty	Returns immediately (null if empty)
Q4. Does BlockingQueue use wait() and notify() internally?

Not directly. Modern JDK implementations typically use ReentrantLock with Condition objects (await()/signal()), which provide more flexibility than intrinsic monitors. The coordination concepts are the same as the ones you've implemented manually.
