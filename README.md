# Successful Referral API

## 📌 Problem Description
This API enables user signup with referral code tracking, where a referral is considered successful **only after the referred user completes their profile**.

For instance, if **User A** signs up using **User B's referral code**, the referral is **only counted** once **User A** completes their profile.

---

## 🚀 Features & Expected Output
### 1️⃣ **User Signup**
- Allows signup with/without a referral code.
- Generates a unique referral code for each user.
- Validates referral code if provided.

### 2️⃣ **Referral Tracking**
- Tracks referral status.
- Links referrer and referred user.
- Marks referral as successful upon profile completion.

### 3️⃣ **API Endpoints**
| Method | Endpoint | Description |
|--------|-------------|--------------|
| **POST** | `/api/v1/users` | User signup with optional referral code |
| **GET** | `/api/v1/users/{idOrEmail}` | Retrieve user details by ID or Email |
| **POST** | `/api/v1/users/complete-profile/{idOrEmail}` | Mark user profile as completed |
| **GET** | `/api/v1/users/referrals/{userIdOrEmail}` | Fetch all referrals of a user |
| **GET** | `/api/v1/users/export-referral-report` | Export referral report as CSV (Bonus) |

---

## 🔥 Setup & Running Locally
### 1️⃣ **Clone the repository**
```sh
git clone https://github.com/antariksha-sharma/Successful-Referral.git
cd Successful-Referral
```

### 2️⃣ **Configure Database (MySQL)**
Create a MySQL database:
(This may not be required)
```sql
CREATE DATABASE successful_referral_db;
```
Update `application.yml` for local development:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/successful_referral_db
    username: root # As per your MySQL configuration
    password: root # As per your MySQL configuration
```

### 3️⃣ **Run the Application**
```sh
mvn spring-boot:run
```

---

## 📡 API Testing (Curl Requests)
### ✅ **User Signup**
```sh
curl -X POST "http://localhost:8080/api/v1/users" \
     -H "Content-Type: application/json" \
     -d '{ "name": "John Doe", "email": "john@example.com", "referralCode": "ABC123" }'
```

### ✅ **Complete Profile**
```sh
curl -X POST "http://localhost:8080/api/v1/users/complete-profile/john@example.com" \
     -H "Content-Type: application/json" \
     -d '{ "phone": "1234567890", "address": "123 Main St" }'
```

### ✅ **Get User Referrals**
```sh
curl -X GET "http://localhost:8080/api/v1/users/referrals/john@example.com"
```

### ✅ **Export Referral Report (CSV)**
```sh
curl -X GET "http://localhost:8080/api/v1/users/export-referral-report" -o referral_report.csv
```

---

## 🛠️ Tech Stack
- **Spring Boot** (Java)
- **MySQL** (Database)
- **Apache POI** (For Excel CSV generation)
- **Spring Data JPA** (ORM)
- **Lombok** (Boilerplate Reduction)

---

## ✅ Coding Guidelines
- **Write clean, readable code**
- **Add meaningful comments**
- **Create a comprehensive README**
- **Implement unit test cases**

---

## 🎖️ Bonus Feature: CSV Report Generation
- An endpoint is provided to export all users and their referrals in CSV format.
- Uses **Apache POI** for structured Excel report generation.

---

## 🚀 Future Enhancements
- Add **pagination** for referral listings.
- Implement **JWT-based authentication** for security.

---

💡 **Developed with ❤️ by [Antariksha Sharma](https://github.com/antariksha-git)** (Click to see the main account).

