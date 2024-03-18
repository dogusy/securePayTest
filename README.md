# securePayTest

Restful api consist two Payment and Customer. Relational database used and tables will create-drop every time application run. Endpoints are secured with Bearer token. Credit-card information stored encryped via AES. Swagger has implemented on this project to test endpoints.
Technologies:
  -Java 21
  -Spring Boot 3.2.3
  -Spring Security 6
  -Lombok
  -PostgreSQL
  -JUnit
  -jsonwebtoken
To start project clone it on your system and create database instance in your system. 


Class	Class, %	Method, %	Line, %
CardService	100% (1/1)	100% (4/4)	71.1% (27/38)
CustomerService	100% (1/1)	100% (5/5)	86% (37/43)
PaymentService	100% (1/1)	100% (6/6)	61.8% (55/89)
