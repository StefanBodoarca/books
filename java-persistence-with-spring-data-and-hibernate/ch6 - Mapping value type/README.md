## Chapter 6 - Mapping value types

### Table of contents

- [Basic properties](#61-basic-properties)
  - [Overriding basic property defaults](#611-overriding-basic-property-defaults)
  - [Customizing property access](#612-customizing-property-access)
  - [Using derived properties](#613-using-derived-properties)
  - [Transforming column values](#614-transforming-column-values)
  - [Generated and default property types](#615-generated-and-default-property-types)
- [Mapping embeddable components](#62-mapping-embeddable-components)
  - [The database schema](#621-the-database-schema)
  - [Making classes embeddable](#622-making-classes-embeddable)
  - [Overriding embedded attributes](#623-overriding-embedded-attributes)
  - [Mapping nested embedded components](#624-mapping-nested-embedded-components)
- [Mapping Java and SQL types with converters](#63-mapping-java-and-sql-types-with-converters)
  - [Built-in types](#631-built-in-types)
  - [Creating custom JPA converters](#632-creating-custom-jpa-converters)
  - [Extending Hibernate with UserTypes](#633-extending-hibernate-with-usertypes)
- [Summary](#summary)

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


The JPA specification offers the @Access annotation for overriding the default behavior, using the parameters AccessType.FIELD (access through fields)
and AccessType.PROPERTY (access through getters). When you set @Access on the class or entity level, all properties of the class 
will be accessed according to the selected strategy. 
Any other mapping annotations, including the @Id, can be set on either fields or getter methods.
We can also use the @Access annotation to override the access strategy of individual properties.

### 6.1.3 Using derived properties

The value of a derived property is calculated at runtime by evaluating an SQL expression declared with the @org.hibernate.annotations.Formula annotation.

```java
@Formula(
    "CONCAT(SUBSTR(DESCRIPTION, 1, 12), '...')"
)
private String shortDescription;
```

The SQL formulas are evaluated every time the Item entity is retrieved from the database and not at any other time, 
so the result can become outdated if other properties are modified. The properties never appear in an SQL INSERT or UPDATE, only in SELECTs. Evaluation occurs in the database; the SQL formula is embedded in the SELECT clause when loading the instance.

### 6.1.4 Transforming column values

```java
@Column(name = "IMPERIALWEIGHT")
@ColumnTransformer(
    read = "IMPERIALWEIGHT / 2.20462",
    write = "? * 2.20462"
)
private double metricWeight;
```

### 6.1.5 Generated and default property types

Examples of database-generated values are creation timestamps, a default price for an item, 
or a trigger that runs for every modification.

Typically, Hibernate (or Spring Data JPA using Hibernate) applications need to refresh 
instances that contain properties for which the database generates values after saving. 
This means the application would have to make another round trip to the database to read 
the value after inserting or updating a row. Marking properties as generated, however, 
lets the application delegate this responsibility to Hibernate or Spring Data JPA using Hibernate. 
Essentially, whenever an SQL INSERT or UPDATE is issued for an entity that has declared 
generated properties, the SQL does a SELECT immediately afterward to retrieve the generated values.

```java
   @CreationTimestamp
   private LocalDate createdOn;

   @UpdateTimestamp
   private LocalDateTime lastModified;
   
   @Column(insertable = false)
   @ColumnDefault("1.00")
   @Generated(org.hibernate.annotations.GenerationTime.INSERT)
   private BigDecimal initialPrice;
```

### 6.1.7 Mapping enumerations

```java
public enum AuctionType {
    HIGHEST_BID,
    LOWEST_BID,
    FIXED_PRICE
}

@NotNull
@Enumerated(EnumType.STRING)
private AuctionType auctionType = AuctionType.HIGHEST_BID;

```


### 6.2 Mapping embeddable components

### 6.2.1 The database schema

<img src="images/db-schema.png" width="230" height="250" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

This schema reflects value type semantics: a particular Address can’t be shared; 
it doesn’t have its own identity. Its primary key is the mapped database identifier 
of the owning entity. An embedded component has a dependent lifecycle: 
when the owning entity instance is saved, the component instance is saved. 
When the owning entity instance is deleted, the component instance is deleted. 
No special SQL needs to be executed for this; all the data is in a single row.

### 6.2.2 Making classes embeddable

Use @Embeddable on the Address class to make it embeddable.

```java
@Embeddable
public class Address {
	@NotNull /*ignored by the DDL generation */
	@Column(nullable = false, length = 5) /* used for DDL generation* ; 
                                            length will override the default
                                            generation of a column as VARCHAR(255) */
	private String zipcode;
	
	@NotNull
    @Column(nullable = false) /* defaults to VARCHAR(255) */
    private String city;
    

	public Address() {
        /**
         * Hibernate call this no-argument constructor
         * to create an instance and then populate the fields directly.
         */
    }
}

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;
	private Address homeAddress; // The Address class is embeddable, so no annotation is needed here.
    // ... 
}
```

_Field-based access_ — When you use field-based access, you can omit getter methods for 
the fields that should not be exposed. Also, fields are declared on a single line, 
while accessor methods are spread out on multiple lines, so field-based access 
will make the code more readable.

_Property-based access_ — Accessor methods may execute additional logic. 
If this is what you want to happen when persisting an object, 
you can use property-based access. If the persistence would like to avoid 
these additional actions, you can use field-based access.

### 6.2.3 Overriding embedded attributes

```java
@Entity
@Table(name = "USERS")
public class User {
	@Embedded
	@AttributeOverride(name = "street", column = @Column(name = "BILLING_STREET"))
	@AttributeOverride(name = "zipcode", column = @Column(name = "BILLING_ZIPCODE", length = 5))
	@AttributeOverride(name = "city", column = @Column(name = "BILLING_CITY"))
	private Address billingAddress;

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) { 
		this.billingAddress = billingAddress;
	}
}
```

Each @AttributeOverride annotation for a component property is “complete”; 
any JPA or Hibernate annotation on the overridden property is ignored. 
This means the @Column annotations on the Address class are ignored, 
so all BILLING_* columns are NULLable! 
(Bean Validation still recognizes the @NotNull annotation on the component property, 
though; only the persistence annotations are overridden.)

### 6.2.4 Mapping nested embedded components

<img src="images/nested_embedded_components.png" width="500" height="250" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

```java
@Embeddable
public class Address {
    @NotNull
    @Column(nullable = false)
    private String street;
		
    @NotNull
    @AttributeOverride(
         name = "name",
         column = @Column(name = "CITY", nullable = false)
    )
    private City city; // ...
}

@Embeddable
public class City {
    @NotNull
    @Column(nullable = false, length = 5)
    private String zipcode;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false) private String country; // ...
}
```

### 6.3 Mapping Java and SQL types with converters

### 6.3.1 Built-in types

<img src="images/primitive_type_mappings.png" width="500" height="280" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

Name column entries are Hibernate-specific.


CHARACTER TYPES\
<img src="images/strings_mapping_to_ansi.png" width="500" height="280" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

DATE AND TIME TYPES\
<img src="images/date_and_time_types.png" width="550" height="400" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

BINARY AND LARGE VALUE TYPES\
<img src="images/binary_and_large_value_types.png" width="550" height="200" alt="">\
(Credits: [Java Persistence with Spring Data and Hibernate](https://www.manning.com/books/java-persistence-with-spring-data-and-hibernate))

- how to work with image, video, audio or clob (character large object - character
data stored in a separate location than the table only references)

### 6.3.2 Creating custom JPA converters

```java
@NotNull
@Convert(converter = MonetaryAmountConverter.class) //custom converters
@Column(name = "PRICE", length = 63)
private MonetaryAmount buyNowPrice;

@Converter
public class MonetaryAmountConverter
        implements AttributeConverter<MonetaryAmount, String> {
    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }
    @Override
    public MonetaryAmount convertToEntityAttribute(String s) {
        return MonetaryAmount.fromString(s);
    }
}
```

Converters aren’t limited to custom classes—we can even override Hibernate’s
built-in type adapters. For example, we could create a custom converter for some or
even all java.util.Date properties in the domain model.

Some limitations of the JPA converters are as follows:
- We can’t apply them to identifier or version properties of an entity.
- We shouldn’t apply a converter on a property mapped with @Enumerated or
@Temporal because these annotations already declare what kind of conversion
has to occur. If we want to apply a custom converter for enums or date/time
properties, we shouldn’t annotate them with @Enumerated or @Temporal.

### 6.3.3 Extending Hibernate with UserTypes

The standardized JPA converters don’t support
the transformation of values from or to multiple columns. Another limitation of JPA
converters is integration with the query engine. We can’t write the following query:
select i from Item i where i.buyNowPrice.amount > 100. Thanks to the converter
from the previous section, Hibernate knows how to convert a MonetaryAmount to and
from a string. However, it doesn’t know that MonetaryAmount has an amount attribute,
so it can’t parse such a query. 

A simple solution would be to map MonetaryAmount as @Embeddable, as you
saw earlier in this chapter for the Address class (listing 6.13). Each property of
MonetaryAmount—amount and currency—maps to its respective database column.
The database admins, however, added a twist to their requirements: because other
old applications also access the database, we’ll have to convert each amount to a target
currency before storing it in the database. For example, Item#buyNowPrice should be
stored in US dollars, and Item#initialPrice should be stored in Euros.

#### The extension points

org.hibernate.usertype package

- _**UserType**_—You can transform values by interacting with the plain JDBC:
  PreparedStatement (when storing data) and ResultSet (when loading data).
  By implementing this interface, you can also control how Hibernate caches and
  dirty-checks values.
- _**CompositeUserType**_—You can tell Hibernate that the MonetaryAmount component has two properties: amount and currency. You can then reference these
  properties in queries with dot notation, such as select avg(i.buyNowPrice
  .amount) from Item i.
- _**ParameterizedType**_—This provides settings to the adapter in mappings. We
  could implement this interface for the MonetaryAmount conversion because, in
  some mappings we’ll want to convert the amount to US dollars and in other
  mappings to Euros. We’ll only have to write a single adapter and we can then
  customize its behavior when mapping a property.
- _**DynamicParameterizedType**_
- _**EnhancedUserType**_
- _**UserVersionType**_
- _**UserCollectionType**_


#### USING TYPE DEFINITIONS

```java
Path: Ch06/mapping-value-types4/src/main/java/com/manning/javapersistence/ch06/converter/package-info.java

@org.hibernate.annotations.TypeDefs({
 @org.hibernate.annotations.TypeDef(
 name = "monetary_amount_usd",
 typeClass = MonetaryAmountUserType.class,
 parameters = {@Parameter(name = "convertTo", value = "USD")}
 ),
 @org.hibernate.annotations.TypeDef(
 name = "monetary_amount_eur",
 typeClass = MonetaryAmountUserType.class,
 parameters = {@Parameter(name = "convertTo", value = "EUR")}
 )
})

package com.manning.javapersistence.ch06.converter;
import org.hibernate.annotations.Parameter;
```


Summary
- You can map the basic and embedded properties of an entity class.
- You can override basic mappings, change the name of a mapped column, use
derived, default, temporal, and enumeration properties, and test them.
- You can implement embeddable component classes and create fine-grained
domain models.
- You can map the properties of several Java classes in a composition, such as
Address and City, to one entity table.
- Any JPA provider supports a minimum set of Java-to-SQL type conversions, as
well as some additional adapters.
- You can write a custom type converter, as we did for the MonetaryAmount class,
with the standard JPA extension interfaces. You can also write a low-level
adapter, as we did with the native Hibernate UserType API