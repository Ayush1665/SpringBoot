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
server.servlet.context-path=/api

# Database (Use Environment Variables)
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.root=INFO
logging.level.com.example=INFO
logging.file.name=logs/application.log

# Spring Security (Use env variables in production)
spring.security.user.name=${APP_USERNAME:user}
spring.security.user.password=${APP_PASSWORD:password}

# Email Configuration
spring.mail.host=${MAIL_HOST} 
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

##  How to Run

```bash
git clone https://github.com/Ayush1665/Employee_Detail_Form.git
cd Employee_Detail_Form
mvn spring-boot:run
```

##  Preview

##### Home Page
![](https://github.com/user-attachments/assets/73f9995b-f98c-44a3-8ca8-a46109f118f5)

##### Update Employee Details
![Update Employee Detail](https://github.com/user-attachments/assets/b2a6b146-d9be-4944-bd38-0ad0914d52ae)

##### Create Employee
![Update Employee Detail](https://github.com/user-attachments/assets/8dd45e9b-cbc6-4252-8515-90f5098bdb7b)

##### Validation
![Validations](https://github.com/user-attachments/assets/9bff7d6c-e29d-41a7-bc8a-f2a2dc7b35b4)
