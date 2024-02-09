## Chapter 6 Using aspects with Spring AOP

Aspects are **a way the framework intercepts method calls and possibly alters the execution
of methods**. You can affect the execution of specific method calls you select.
This technique **helps you extract part of the logic belonging to the executing method**.
In certain scenarios, **decoupling a part of the code helps make that method easier to
understand**. It allows the developer to focus only on the relevant details discussed
when reading the method logic.

Another important reason for learning aspects is that Spring uses them in implementing
a lot of the crucial capabilities it offers (e.g. _transactionality_, _security configurations_).

### 6.1 How aspects work in Spring

An aspect is simply a piece of logic the framework executes when you call specific
methods of your choice. When designing an aspect, you define the following:
- **What code you want Spring to execute when you call specific methods**. This is
named an **_aspect_**.
- **When the app should execute this logic of the aspect** (e.g., before or after the
method call, instead of the method call). This is named the **_advice_**.
- **Which methods the framework needs to intercept and execute the aspect for
them**. This is named a **_pointcut_**.

With aspects terminology, you’ll also find the concept of a **_join point_**, which defines the
event that triggers the execution of an aspect. **But with Spring, this event is always a
method call.**

As in the case of the DI, to use aspects you need the framework
to manage the objects for which you want to apply aspects.

**The bean that declares the method intercepted by an aspect** is named the **_target object_**.

<img src="images/aspects_terminology.png" width="600" height="600" alt="">\
(Credits: [Spring Start Here](https://www.manning.com/books/spring-start-here))

But how does Spring intercept each method call and apply the aspect logic?

Spring **won’t directly give you an instance reference for the bean** when you request it from the context. Instead, **Spring
gives you an object that calls the aspect logic** instead of the actual method. We say that
**Spring gives you a _proxy_ object** instead of the real bean. You will now receive the proxy
instead of the bean anytime you get the bean from the context, either if you directly
use the _getBean()_ method of the context or if you use DI. This approach is named _weaving_. So, when we define an aspect for a method,
the call goes through the _proxy object_. The _proxy object_ applies the logic defined by the aspect and then delegates the call to the real method.

### 6.2 Implementing aspects with Spring AOP

