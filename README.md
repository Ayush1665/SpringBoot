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

##### ER Diagram
![WhatsApp Image 2026-03-31 at 10 09 25 PM](https://github.com/user-attachments/assets/44fafe83-c168-4ae7-a5db-2e1733b063f4)

##### Create Employee
![](https://github.com/user-attachments/assets/73f9995b-f98c-44a3-8ca8-a46109f118f5)

##### Update Employee Details
![Update Employee Detail](https://github.com/user-attachments/assets/b2a6b146-d9be-4944-bd38-0ad0914d52ae)

##### Validation
![Validations](https://github.com/user-attachments/assets/9bff7d6c-e29d-41a7-bc8a-f2a2dc7b35b4)

##### Swagger Integration
<img width="1347" height="597" alt="Screenshot 2026-04-10 113913" src="https://github.com/user-attachments/assets/7f526859-d873-43aa-b784-8349de2693f6" />

##### Jacoco (Java Code Coverage)
![WhatsApp Image 2026-03-31 at 10 04 17 PM](https://github.com/user-attachments/assets/e4834bc8-c6b2-4f1e-a93a-bb20e70511e7)
