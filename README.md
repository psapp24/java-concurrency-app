Goal

Replace the single shared notification with an actual queue.

Architecture:

Publisher
      |
      v
+---------------------+
|   LinkedList Queue  |
+---------------------+
      |
      v
Consumer

Here we'll intentionally not use:

❌ synchronized
❌ wait()
❌ notify()
❌ BlockingQueue

We'll first observe the problems this naive implementation introduces:

Multiple notifications can now be stored.
The consumer still has to busy wait when the queue is empty.
The queue is not thread-safe, so concurrent access can lead to inconsistent behavior.

This naturally motivates why synchronization and coordination mechanisms are needed.

What problems still exist?

This implementation is intentionally incomplete. It still has several issues that we'll solve step by step:

Busy waiting: When the queue is empty, the consumer wakes up every second to check again, wasting CPU cycles.
Not thread-safe: LinkedList is not safe for concurrent access. With multiple publishers or consumers, data corruption or inconsistent results can occur.
No coordination: The consumer has no way to sleep until new data arrives.
No graceful shutdown: The consumer loops forever until interrupted.
