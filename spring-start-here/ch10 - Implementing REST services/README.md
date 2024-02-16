## Chapter 10 Implementing REST services

Check the code on [sq-c9-ex2](sq-c9-ex2/src/main/java/com/ro).

### 10.1 Using REST services to exchange data between apps

<img src="images/rest_springmvc_flow.png" width="700" height="600" alt="">\
(Credits: [Spring Start Here](https://www.manning.com/books/spring-start-here))

Issues the REST endpoint might bring:
- If the controller’s action takes a long time to complete, the HTTP call to the
endpoint might time out and break the communication.
- Sending a large quantity of data in one call (through the HTTP request) might
cause the call to time out and break the communication. Sending more than a
few megabytes through a REST call usually isn’t the right choice.
- Too many concurrent calls on an endpoint exposed by a backend component
might put too much pressure on the app and cause it to fail.
- The network supports the HTTP calls, and the network is never 100% reliable.
There’s always a chance a REST endpoint call might fail because of the network.

Questions to ask:
- What should happen if a call fails and how it might affect the app?
- The data could be affected in any way if a call fails?
- Could the way you designed your app lead to data inconsistencies if an endpoint call fails? 
- In case the app needs to display an error to the user, how would you do that?

Recommended reading: [API Design Patterns (Manning, 2021)](https://www.manning.com/books/api-design-patterns)

### 10.2 Implementing a REST endpoint

