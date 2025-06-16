# 💰 Personal Finance Manager

<img src="https://cdn-icons-png.flaticon.com/512/4299/4299956.png" width="120" align="right"/>

A Spring Boot-based web application to manage income, expenses, categories, savings goals, and generate financial reports with clear analytics.

---

## 🚀 Features

✅ User Registration/Login (Session-based)  
✅ Transaction CRUD + Filters  
✅ Predefined & Custom Categories  
✅ Savings Goal Tracker  
✅ Monthly/Yearly Reports  

<img src="https://cdn-icons-png.flaticon.com/512/3523/3523887.png" width="400"/>

---

## ⚙️ Tech Stack

<img src="https://cdn-icons-png.flaticon.com/512/226/226777.png" width="30"/> Java 17  
<img src="https://spring.io/images/projects/spring-boot-7f2e24fb962501672cc91ccd285ed2ba.svg" width="30"/> Spring Boot 3.x  
🔒 Spring Security  
🗃️ Spring Data JPA  
🧪 JUnit 5 + Mockito  
🗄️ H2 Database  
🚀 Render Deployment  

---

## 🧪 Run Locally


git clone https://github.com/yourusername/finance-manager.git
cd finance-manager
mvn clean spring-boot:run

## ⚙️ Access

- **H2 Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- **🔐 API Base URL:**  
  https://your-render-url.onrender.com/api`

---

## 📄 API Endpoints

## 🔐 Auth

- POST /auth/register
- POST /auth/login
- POST /auth/logout

### 💸 Transactions

- GET /transactions
- POST /transactions
- PUT /transactions/{id}
- DELETE /transactions/{id}

### 🗂️ Categories

- GET /categories
- POST /categories
- DELETE /categories/{name}

### 🎯 Goals

- GET /goals
- POST /goals
- PUT /goals/{id}
- DELETE /goals/{id}

### 📊 Reports

- GET /reports/monthly/{year}/{month}
- GET /reports/yearly/{year}

---

## ✅ Testing

Run the test script:


bash financial_manager_tests.sh https://your-render-url.onrender.com/api

## 📝 License
MIT License © 2025

## 🙏 Credits
Assignment provided by **Syfe**  
Developed by **Tisha Mahato**

---

