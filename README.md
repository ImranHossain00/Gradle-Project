# Tech Cottage 
Author: Md Imran Hossain
<br>
The project is ongoing. It is developed using Java, Servlet, and JSP—a simple e-commerce project where
users can perform actions such as adding products to the shopping cart, making purchases, placing orders,
and checking the prices of the products.

## About
<hr>
This project is simply a demo demonstrating basic features which is only the tip of an iceberg.
This project might (and certainly does) have bugs, vulnerabilities or some other types of issues.
So, contributions are always welcome :)

I attempted to implement this small project using the principles of `Object-Oriented Programming (OOP)`.
Specifically, I applied the concept of `Loosely Coupled` design by utilizing interfaces.
I also applied the concept of `Singleton Pattern` in [ValidationUtil](https://github.com/ImranHossainFakir/Shopping-Cart/blob/main/src/main/java/com/techCottage/utils/ValidationUtil.java) and [ConnectionPool](https://github.com/ImranHossainFakir/Shopping-Cart/blob/main/src/main/java/com/techCottage/jdbcPool/ConnectionPool.java) classes. Each interface is designed to handle a single, specific task.
However, the implementation may not fully adhere to `OOP` principles.

In this project there have multiple packages in the [src/main](https://github.com/ImranHossainFakir/Shopping-Cart/blob/main/src/main) directory.

1. [java/com/imran](#1-javacomimran)
2. [resources](#2-resources)
3. [webapp](#3-webapp)

### 1. java/com/imran
- `annotations`: Holding customized annotation which is used in `DTO(Data Transfer Objec)` classes.
- `domain`: Classes that are responsible to communicate with the respective tables in database.
- `dto`: Classes or objects handling data communication with the client, sending and receiving information.
- `jdbc`: ConnectionPool object which is responsible to create connection with database, store connections to the pool.
- `repository`: Objects which are responsible for the `CRUD` operations.
- `services`: Holds the Business Logics.
- `util`: Validator classes.
- `servlets`: Servlet classes which are responsible for receiving requests and sending responses.

## Features Include
<hr>

## Prerequisites
My device is equipped with:
```
Linux Operating System (Ubuntu 23.04 [lunar])
JDK - 11
Tomcat server 9.
Locally installed MySQL.
Compatible IDE, Intellij IDEA recommended for this project.
Gradle 7.2 (Build Tool)
```

## Database and Tables
<hr>

## Errors and Arrived Questions
<hr>
When attempting to insert signup data into our database, we encountered an exception related to the
database. The issue arose due to the size of the password field. Initially, I declared the size of the password field as `varchar(60)`.
However, the encrypted password exceeded this limit. I subsequently increased the size to `100` characters,
and now it is functioning properly.

