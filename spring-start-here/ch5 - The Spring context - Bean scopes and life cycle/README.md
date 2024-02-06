## Chapter 5 The Spring Context: Bean scopes and life cycle

### Table of contents
- [Using the singleton bean scope](#51-using-the-singleton-bean-scope)
  - [How singleton beans work](#511-how-singleton-beans-work)
  - [Singleton beans in real-world scenarios](#512-singleton-beans-in-real-world-scenarios)
  - [Using eager and lazy instantiation](#513-using-eager-and-lazy-instantiation)
  

Spring has multiple different approaches for creating beans and managing their
life cycle, and in the Spring world we name these approaches _scopes_. In this chapter, we
discuss two scopes you’ll often find in Spring apps: _singleton_ and _prototype_.

### 5.1 Using the singleton bean scope

The singleton bean scope defines Spring’s default approach for managing the beans
in its context. It is also the bean scope you’ll most encounter in production apps.

### 5.1.1 How singleton beans work

<img src="images/singleton_pattern_vs_scope.png" width="700" height="800" alt="">

**Declaring single-scoped bean with _@Bean_**

Check the code on [ProjectConfig](sq-c5-ex1/src/main/java/com/ro/config/ProjectConfig.java) and [Main class](sq-c5-ex1/src/main/java/com/ro/Main.java).

**Declaring singleton beans using stereotype annotations**

Spring’s behavior for singleton beans isn’t any different when
using stereotype annotations than when you declared them with the @Bean annotation.

<img src="images/class_design_singleton_stereotype.png" width="500" height="500" alt="">

Check the code on [sq-c5-ex2](sq-c5-ex2/src/main/java/com/ro) and see how Spring injected the same instance of CommentRepository on both services.

### 5.1.2 Singleton beans in real-world scenarios

Because the singleton bean scope assumes that multiple components of the app
can share an object instance, **the most important thing to consider is that these beans
must be immutable**. Most often, a real-world app executes actions on multiple threads
(e.g., any web app). In such a scenario, multiple threads share the same object
instance. If these threads change the instance, you encounter a race-condition scenario.

If you want mutable singleton beans (whose attributes change), you need to make
these beans concurrent by yourself (mainly by employing thread synchronization). **But
singleton beans aren’t designed to be synchronized. They’re commonly used to define
an app’s backbone class design and delegate responsibilities one to another.** Technically,
synchronization is possible, but it’s not a good practice. Synchronizing the thread on a concurrent instance can dramatically affect the app’s performance. In most cases,
you will find other means to solve the same problem and avoid thread concurrency.

**Using beans boils down to three points:**
- Make an object bean in the Spring context only if you need Spring to manage it
so that the framework can augment that bean with a specific capability. If the
object doesn’t need any capability offered by the framework, you don’t need to
make it a bean.
- If you need to make an object bean in the Spring context, it should be singleton
only if it’s immutable. Avoid designing mutable singleton beans.
- If a bean needs to be mutable, an option could be to use the prototype scope.

### 5.1.3 Using eager and lazy instantiation

