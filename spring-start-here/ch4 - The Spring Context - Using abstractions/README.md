## Chapter 4 The Spring Context: Using abstractions

### 4.1 Using interfaces to define contracts

<img src="images/plain_java_app_design.png" width="600" height="500" alt="">

Check [sq-c4-ex1](sq-c4-ex1/src/main/java/com/ro) to see the code.

### 4.2 Using dependency injection with abstractions

Moving the above classes to Spring.

### 4.2.1 Deciding which objects should be part of the Spring context

For our scenario, the only Spring feature we use is the **DI**. In this case, we need to add the object to the Spring context if it
either **has a dependency we need to inject from the context** or **if it’s a dependency**
itself. Looking at our implementation, you’ll observe that the only object that doesn’t
have a dependency and is also not a dependency itself is _Comment_.

But why not add the _Comment_ instances as well?\
Adding objects to the Spring context without needing the
framework to manage them **adds unnecessary complexity to your app**, making the app
both more challenging to maintain and less performant. When you add an object to
the Spring context, you allow the framework to manage it with some specific functionality
the framework provides. If you add the object to be managed by Spring without
getting any benefit from the framework, you just over-engineer your implementation.

**NOTE** We use stereotype annotations for the classes that Spring needs to create instances and add
these instances to its context. It doesn’t make sense to add stereotype annotations on
interfaces or abstract classes because these cannot be instantiated. Syntactically, you
can do this, but it is not useful.

<img src="images/using_di_with_abstractions_1.png" width="600" height="600" alt="">
