#  Production Ready Spring Boot Web Application

A backend application built using **Spring Boot**, **Spring MVC**, **Spring Data JPA (Hibernate)**, **Criteria Builder**, and **MySQL**.

This project follows clean layered architecture and implements dynamic query building, pagination, sorting, logging, and Spring Security configuration.

##  Technologies Used

* **Backend:** Spring Boot, Spring MVC
* **ORM:** Spring Data JPA (Hibernate)
* **Dynamic Queries:** CriteriaBuilder & CriteriaQuery
* **Database:** MySQL
* **Security:** Spring Security
* **Logging:** SLF4J + Logback
* **Build Tool:** Maven
* **Java Version:** 17+

##  Spring Security

Default credentials are configured using environment variables.

Example:

```
Username: user
Password: password
```

(For production, always change credentials and use encrypted passwords.)


##  Production `application.properties`

```properties
spring.application.name=Employee_Detail_Form

# Server
server.port=${PORT:8080}
server.servlet.context-path=/abc

# Database (Use Environment Variables)
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# Logging
logging.level.root=INFO
logging.level.com.example=INFO
logging.file.name=logs/application.log

# Spring Security (Use env variables in production)
spring.security.user.name=${APP_USERNAME:user}
spring.security.user.password=${APP_PASSWORD:password}
```

##  How to Run

```bash
git clone https://github.com/Ayush1665/Employee_Detail_Form.git
cd Employee_Detail_Form
mvn spring-boot:run
```
