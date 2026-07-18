Execution Flow

Initially:

Queue Empty

↓

Consumer enters consume()

↓

Queue is empty

↓

Consumer calls wait()

↓

Consumer goes to WAITING state

Publisher starts:

Publisher publishes Message-1

↓

notify()

↓

Consumer wakes

↓

Consumes Message-1

↓

Queue Empty again

↓

wait()

This repeats.

Thread States
Consumer

RUNNABLE

↓

wait()

↓

WAITING

↓

notify()

↓

BLOCKED (waiting to reacquire monitor)

↓

RUNNABLE

↓

Consumes message

↓

wait()

↓

WAITING

This sequence is very important for interviews.

What did we improve?
Part 2
Queue Empty

↓

Sleep 1 second

↓

Wake

↓

Check again
Part 3
Queue Empty

↓

wait()

↓

Sleep forever

↓

notify()

↓

Wake instantly

This is much more efficient.

Interview Questions Covered

By the end of Part 3, you'll be able to answer:

Why must wait() be inside a synchronized block or synchronized method?
Why must notify() also hold the same monitor?
Why use while instead of if around wait()?
What object owns the monitor?
What happens to the monitor when wait() is called?
Difference between sleep() and wait()
Difference between WAITING and BLOCKED thread states
Why is polling considered inefficient?
