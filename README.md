<div align="center">

# 🛒 E-Commerce REST API
### Built with Spring Boot 4 · Java 17 · MySQL · JPA

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Lombok](https://img.shields.io/badge/Lombok-Enabled-pink?style=for-the-badge)](https://projectlombok.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

A fully-featured **E-Commerce backend REST API** built using Spring Boot with layered architecture, JPA/Hibernate ORM, and MySQL. Supports product management, customer operations, authentication, order lifecycle tracking, and advanced product analytics.

</div>

---

## 📌 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Data Models](#-data-models)
- [API Reference](#-api-reference)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Exception Handling](#-exception-handling)
- [Author](#-author)

---

## ✨ Features

- 🔐 **Authentication** — Customer signup, login & email lookup
- 👤 **Customer Management** — Full CRUD with membership tiers & status tracking
- 📦 **Product Management** — Rich product catalog with 20+ query endpoints
- 📊 **Advanced Analytics** — Grouping, sorting, partitioning, price statistics
- 🏷️ **Membership System** — BRONZE / SILVER / GOLD / DIAMOND tiers
- 🚚 **Order Lifecycle** — Full order status tracking from placement to return
- 💳 **Payment Tracking** — Multiple payment modes and status management
- 📍 **Address Management** — Multiple address types per customer
- ⚠️ **Global Exception Handling** — Consistent error responses across all endpoints

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 4.0.6 |
| ORM | Spring Data JPA + Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Boilerplate Reduction | Lombok |
| Dev Tools | Spring Boot DevTools |

---

## 📁 Project Structure

```
ecommerce-springboot/
│
├── src/main/java/com/ecommerce/
│   │
│   ├── EcommerceApplication.java          # 🚀 Main entry point
│   │
│   ├── controller/                        # REST Controllers (API layer)
│   │   ├── AuthController.java            # Signup, Login, Email check
│   │   ├── CustomerController.java        # Customer CRUD
│   │   └── ProductController.java         # Product CRUD + Analytics
│   │
│   ├── service/                           # Business Logic layer
│   │   ├── AuthService.java               # Auth interface
│   │   ├── AuthServiceImpl.java           # Auth implementation
│   │   ├── CustomerService.java           # Customer interface
│   │   ├── CustomerServiceImpl.java       # Customer implementation
│   │   ├── ProductService.java            # Product interface
│   │   └── ProductServiceImpl.java        # Product implementation
│   │
│   ├── repository/                        # Data Access layer (JPA)
│   │   ├── CustomerRepository.java
│   │   └── ProductRepository.java
│   │
│   ├── entity/                            # JPA Entities (DB Tables)
│   │   ├── Customer.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── Address.java
│   │   └── payment.java
│   │
│   ├── dto/                               # Data Transfer Objects
│   │   └── ProductDto.java
│   │
│   ├── model/                             # Request models
│   │   └── LoginCredentials.java
│   │
│   ├── enums/                             # Enumerations
│   │   ├── Gender.java
│   │   ├── Membership.java                # BRONZE, SILVER, GOLD, DIAMOND
│   │   ├── Status.java
│   │   ├── AddressType.java
│   │   ├── OrderStatus.java               # PLACED → DELIVERED → RETURNED
│   │   ├── PaymentMode.java
│   │   └── PaymentStatus.java
│   │
│   └── exception/                         # Custom Exceptions
│       ├── GlobalExceptionHandler.java    # Centralized error handling
│       ├── BadRequestException.java
│       ├── CustomerExistsException.java
│       ├── CustomerNotFoundException.java
│       ├── InvalidCredentialsException.java
│       ├── ProductExistsException.java
│       └── ProductNotFoundException.java
│
└── src/main/resources/
    └── application.properties             # App configuration
```

---

## 📐 Data Models

### 👤 Customer
| Field | Type | Description |
|-------|------|-------------|
| id | int | Primary key |
| name | String | Full name |
| email | String | Unique email |
| phoneNo | String | Contact number |
| password | String | Hashed password |
| age | byte | Customer age |
| gender | Gender | MALE / FEMALE / OTHER |
| status | Status | ACTIVE / INACTIVE |
| membership | Membership | BRONZE / SILVER / GOLD / DIAMOND |
| createdOn | LocalDateTime | Account creation time |
| lastLoggedIn | LocalDateTime | Last login timestamp |
| addresses | List\<Address\> | Multiple addresses |

### 📦 Product
| Field | Type | Description |
|-------|------|-------------|
| id | Integer | Primary key |
| name | String | Product name |
| maxRetailPrice | int | MRP |
| discountPercentage | float | Discount % |
| isAvailable | boolean | In stock flag |
| company | String | Brand/Company |
| category | String | Product category |
| manufacturedYear | int | Year of manufacture |

### 🚚 Order Status Flow
```
PLACED → PAYMENT_PENDING → CONFIRMED → SHIPPED → OUT_FOR_DELIVERY → DELIVERED
                                                                         ↓
                                                              RETURN_REQUESTED → RETURNED
                                                                         ↓
                                                                    CANCELLED
```

---

## 📡 API Reference

### 🔐 Auth Endpoints — `/auth`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/signup` | Register a new customer |
| `POST` | `/auth/login` | Login with email & password |
| `GET` | `/auth/exists/{email}` | Get customer by email |

**Signup Request Body:**
```json
{
  "name": "Aiden Kota",
  "email": "aiden@example.com",
  "password": "securePass123",
  "phoneNo": "9876543210",
  "age": 22,
  "gender": "MALE",
  "membership": "SILVER"
}
```

**Login Request Body:**
```json
{
  "email": "aiden@example.com",
  "password": "securePass123"
}
```

---

### 👤 Customer Endpoints — `/api/v1/customers`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Get all customers |
| `GET` | `/{id}` | Get customer by ID |
| `GET` | `/exists/{email}` | Get customer by email |
| `PUT` | `/` | Update customer |
| `DELETE` | `/?id={id}` | Delete customer by ID |

---

### 📦 Product Endpoints — `/api/v1/products`

#### Basic CRUD
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/` | Add a new product |
| `GET` | `/` | Get all products |
| `GET` | `/{id}` | Get product by ID |
| `PUT` | `/{id}` | Update product |
| `DELETE` | `/{id}` | Delete product |

#### 🔍 Filtering & Search
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/availability/{isAvailable}` | Filter by availability |
| `GET` | `/category/{category}` | Filter by category |
| `GET` | `/price-greater-than/{price}` | Filter by min price |
| `GET` | `/manufactured-after/{year}` | Filter by manufacture year |
| `GET` | `/available-price-greater-than?price=` | Available products above price |
| `GET` | `/exists/company/{company}` | Check if company has products |

#### 📊 Analytics & Aggregation
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/names` | Get all product names |
| `GET` | `/categories` | Get unique categories |
| `GET` | `/count/available` | Count available products |
| `GET` | `/all-available` | Check if all products available |
| `GET` | `/inventory-value` | Total inventory value |
| `GET` | `/average-price/by-category` | Avg price grouped by category |
| `GET` | `/count/by-category` | Product count per category |

#### 🏆 Sorting & Ranking
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/sort/price-asc` | Sort by price ascending |
| `GET` | `/sort/name-desc` | Sort by name descending |
| `GET` | `/top-expensive?limit=` | Top N most expensive |
| `GET` | `/top-expensive/by-category` | Top 3 per category |
| `GET` | `/highest-price` | Most expensive product |
| `GET` | `/lowest-price` | Cheapest product |
| `GET` | `/first` | First product in DB |

#### 📁 Grouping & Partitioning
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/grouped/by-category` | Products grouped by category |
| `GET` | `/grouped/by-company` | Products grouped by company |
| `GET` | `/partitioned/by-availability` | Products split by availability |
| `GET` | `/map/by-id` | Products as ID → Product map |

#### 💰 Price Calculation
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/final-price` | Calculate final price after discount |

**Final Price Request Body:**
```json
{
  "maxRetailPrice": 1000,
  "discountPercentage": 20.0
}
```

---

## 🚀 Getting Started

### Prerequisites

- ✅ Java 17+
- ✅ Maven 3.8+
- ✅ MySQL 8+
- ✅ IntelliJ IDEA or VS Code

### 1. Clone the Repository

```bash
git clone https://github.com/kota64453/ecommerce-springboot.git
cd ecommerce-springboot/ecommerce-springboot
```

### 2. Set Up MySQL Database

```sql
CREATE DATABASE ecommerce_db;
```

### 3. Configure application.properties

```properties
spring.application.name=ecommerce-springboot

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Port
server.port=8080
```

### 4. Build & Run

```bash
# Using Maven
mvn clean install
mvn spring-boot:run

# Or using the wrapper
./mvnw spring-boot:run        # Mac/Linux
mvnw.cmd spring-boot:run      # Windows
```

### 5. Test the API

Open your browser or Postman and hit:
```
http://localhost:8080/api/v1/products/
```

---

## ⚠️ Exception Handling

All exceptions are handled globally via `GlobalExceptionHandler` and return consistent JSON error responses.

| Exception | HTTP Status | Trigger |
|-----------|-------------|---------|
| `CustomerNotFoundException` | 404 | Customer ID/email not found |
| `CustomerExistsException` | 409 | Duplicate customer email |
| `ProductNotFoundException` | 404 | Product ID not found |
| `ProductExistsException` | 409 | Duplicate product |
| `InvalidCredentialsException` | 401 | Wrong email/password |
| `BadRequestException` | 400 | Invalid input (negative price, blank category etc.) |

**Error Response Format:**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with id: 5",
  "timestamp": "2025-06-16T10:30:00"
}
```

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch → `git checkout -b feature/amazing-feature`
3. Commit your changes → `git commit -m "Add amazing feature"`
4. Push to the branch → `git push origin feature/amazing-feature`
5. Open a Pull Request

---

## 👨‍💻 Author

**kota64453**  
GitHub: [@kota64453](https://github.com/kota64453)

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

⭐ **If this project helped you, give it a star!** ⭐

Made with ❤️ using Spring Boot

</div>
