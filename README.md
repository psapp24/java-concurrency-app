flow 1
Main
|
v
NotificationController
|
v
NotificationService
|
v
NotificationPublisher
|
v
NotificationQueue
|
v
NotificationConsumer (Thread)
|
v
ExternalApiClient

Publisher
|
v
+-------------------+
| NotificationQueue |
|                   |
| request           |
+-------------------+
|
v
Consumer

Main should be
Main

↓

Create Queue

↓

Create Publisher (inject Queue)

↓

Create Service (inject Publisher)

↓

Create Controller (inject Service)

↓

Create Consumer (inject Queue)

↓

controller.receive(request)

↓

consumer.start()

Sample Output
================================
Consumer State : NEW
================================

main -> Request Received

main -> Validating Request

main -> Validation Successful

main -> Publishing Request

main -> Request added to Queue

================================
Consumer State : NEW
================================

Consumer-1 -> Consumer Started

Consumer-1 -> Processing Request : 1

Consumer-1 -> Calling External API...

================================
Consumer State : TIMED_WAITING
================================

Consumer-1 -> External API completed

Consumer-1 -> Consumer Finished

================================
Consumer State : TERMINATED
================================


| Concept                                                                             | Covered?                             |
| ----------------------------------------------------------------------------------- | ------------------------------------ |
| Thread creation                                                                     | ✅                                    |
| `Thread.start()`                                                                    | ✅                                    |
| `Thread.run()`                                                                      | ✅ (`run()` is executed by `start()`) |
| `Thread.join()`                                                                     | ✅                                    |
| `Thread.sleep()`                                                                    | ✅                                    |
| Thread states (`NEW`, `TIMED_WAITING`, `TERMINATED`)                                | ✅                                    |
| Constructor-based Dependency Injection                                              | ✅                                    |
| Layered Architecture (Controller → Service → Publisher → Queue → Consumer → Client) | ✅                                    |
| Manual wiring (similar to Spring DI)                                                | ✅                                    |
