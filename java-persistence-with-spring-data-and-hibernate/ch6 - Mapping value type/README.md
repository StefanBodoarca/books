## Chapter 6 - Mapping value types

### Table of contents

### 6.1 Basic properties

Rules:
- If the property is a primitive or a primitive wrapper, or of type _String, 
BigInteger, BigDecimal, java.time.LocalDateTime, java.time.LocalDate, 
java.time.LocalDate, java.util.Date, java.util.Calendar, java.sql.Date,
java.sql.Time, java.sql.Timestamp, byte[], Byte[], char[], or Character[]_, 
it’s automatically persistent. Hibernate or Spring Data JPA using Hibernate 
loads and stores the value of the property in a column with an appropriate SQL 
type and the same name as the property.
- Otherwise, if we annotate the class of the property as @Embeddable, 
or we map the property itself as @Embedded, the property is mapped as an embedded component of the owning class.
- Otherwise, if the type of the property is java.io.Serializable, its value is stored in its serialized form. 
This may raise compatibility problems (we might have stored the information using one class format and would 
like to retrieve it later using another class format) and performance problems (the serialization/ deserialization operations are costly). 
We should always map Java classes instead of storing a series of bytes in the database. Maintaining a database with this binary information
when the application may be gone in a few years will mean that the classes that the serialized version maps to are no longer available.
- Otherwise, an exception will be thrown on startup, complaining that the type of the property isn’t understood.

### 6.1.1 Overriding basic property defaults

- To exclude a property, mark the field or the getter method of the property with the annotation @javax.persistence.Transient 
or use the Java transient keyword. The transient keyword excludes fields both 
for Java serialization and for persistence, as it is also recognized by JPA providers. 
The @javax.persistence.Transient annotation will only exclude the field from being persisted.

-
```java
@Basic(optional = false)
BigDecimal initialPrice;

The option shown here, optional, marks the property as not optional at the Java object level.
```
-
```java
@Column(nullable = false)
BigDecimal initialPrice;

We recommend using the Bean Validation @NotNull annotation so that you can manually 
validate an Item instance and have your user interface code
in the presentation layer execute validation checks automatically. 
There isn’t much difference in the end result, but not hitting the database with a statement 
that fails is cleaner.

@Column(name = "START_PRICE", nullable = false)
BigDecimal initialPrice;
```

### 6.1.2 Customizing property access

An annotated entity inherits the default from the position of the mandatory @Id annotation. 
For example, if we declare @Id on a field, rather than using a getter method, all other mapping 
annotations for that entity are expected to be fields. Annotations are not supported on the setter methods.

