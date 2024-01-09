# SpringBootPracticeProject
- 회원 + 게시판 + 채팅 기능 구현을 목표로 하는 연습 프로젝트

## 개발 환경
- IDE: IntelliJ IDEA Ultimate
- Spring Boot 3.1.7
- JDK 17
- MySQL
- Spring Data JPA
- ThymeLeaf

## application.yml
```

server:
  port: 8080
  tomcat:
    max-http-form-post-size: 1GB

# DB
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/board?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: DB 아이디
    password: DB 비밀번호
  thymeleaf:
    cache: false
#Swagger
  mvc:
    path-match:
      matching-strategy: ant_path_matcher

##  JPA
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
#    database-platform: org.hibernate.dialect.H2Dialect
    open-in-view: false
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true

  servlet:
    multipart:
      max-file-size: 1GB
      max-request-size: 1GB
```
