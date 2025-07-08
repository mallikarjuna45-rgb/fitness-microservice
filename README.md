# 🏋️‍♂️ AI Powered Fitness Application

A scalable, cloud-native microservices-based fitness platform built using **Java 21**, **Spring Boot**, **Spring Cloud**, **RabbitMQ**, and **Gemini API**. This backend system handles user profiles, activities, AI recommendations, and more.

---

## 🚀 Tech Stack

- **Language:** Java 21  
- **Frameworks:** Spring Boot, Spring Cloud (Eureka, Config, Gateway)  
- **Databases:** MySQL (User Service), MongoDB (Activity and AI Services)  
- **Messaging System:** RabbitMQ  
- **AI Integration:** Gemini API  
- **API Tools:** Postman, Swagger  
- **IDE:** IntelliJ IDEA  
- **Version Control:** Git & GitHub  

---

## 🧩 Architecture Overview

This application follows a modular **microservices architecture**, consisting of:

- **User Service:** Manages user registration and profile data  
- **Activity Service:** Records fitness activities (steps, time, workout type, etc.)  
- **AI Service:** Consumes activity data asynchronously and uses Gemini API to provide fitness recommendations  
- **API Gateway:** Centralized entry point to route external API calls to microservices  
- **Eureka Server:** Handles service registration and discovery  
- **Spring Cloud Config Server:** Maintains centralized configuration for all services  
- **RabbitMQ:** Enables asynchronous communication between services  

---

## 📡 Communication Flow

- **REST API calls** are routed through Spring Cloud Gateway to appropriate services  
- **Activity Service** publishes activity data to RabbitMQ  
- **AI Service** consumes messages from RabbitMQ and requests recommendations from Gemini API  
- **Recommendations** are processed and returned for each user

---

## 🛠️ RabbitMQ Configuration

RabbitMQ is a key component for asynchronous communication between microservices (such as Activity Service → AI Service).

**Typical Spring Boot RabbitMQ configuration (`application.yml`):**

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

# Define queues, exchanges, and bindings in service configs or Java config
```

**🟢 Quick RabbitMQ Setup with Docker:**

```bash
docker run -d --hostname rabbit --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
```

✅ Access the RabbitMQ dashboard at [http://localhost:15672](http://localhost:15672)  
Login: `guest` | Password: `guest`

---

## 📖 API Reference

### fitness

---

### **User Service**

#### **POST** Register User

`http://localhost:8080/api/users/register`

**Body** (raw, JSON):

```json
{
    "email": "dummyuser@example3.com",
    "password": "password123",
    "firstName": "Jane",
    "lastName": "Smith"
}
```

---

#### **GET** User Validation

`http://localhost:8080/api/users/a968eedc-9ae8-4f72-a7dc-52db5f016a94/validation`

**Body** (raw, text):

```text
{
    "email": "dummyuser@example3.com",
    "password": "password123",
    "firstName": "Jane",
    "lastName": "Smith"
}
```

---

### **Activity Service**

#### **POST** Activity Creation

`http://localhost:8080/api/activities`

**Request Headers:**
- `X-USER-ID`: 12345

**Body** (raw, JSON):

```json
{
    "userId": "a968eedc-9ae8-4f72-a7dc-52db5f016a94",
    "activityType": "RUNNING",
    "duration": 20,
    "startTime": "2023-10-01T10:00:00",
    "caloriesBurned": 404,
    "additionalMetrics": {
        "distance": 5.0,
        "steps": 4000
    }
}
```

---

#### **GET** User Activities

`http://localhost:8080/api/activities`

**Request Headers:**
- `X-USER-ID`: a968eedc-9ae8-4f72-a7dc-52db5f016a94

**Body** (raw, JSON):

```json
{
    "userId": "12345",
    "activityType": "RUNNING",
    "duration": 30,
    "startTime": "2023-10-01T10:00:00",
    "caloriesBurned": 300,
    "additionalMetrics": {
        "heartRate": 150,
        "distance": 5.0,
        "steps": 4000
    }
}
```

---

### **AI Service**

#### **GET** Recommendation

`http://localhost:8080/api/recommendations/user/a968eedc-9ae8-4f72-a7dc-52db5f016a94`

---

### **Gemini API Integration**

#### **POST** Gemini API

`https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=GEMINI_API_KEY`

**Request Headers:**
- `Content-Type`: application/json

**Query Params:**
- `key`: GEMINI_API_KEY

**Body** (raw, JSON):

```json
{
    "contents": [
        {
            "parts": [
                {
                    "text": "Explain how AI works in a few words"
                }
            ]
        }
    ]
}
```

Example cURL:

```bash
curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=GEMINI_API_KEY" \
  -H 'Content-Type: application/json' \
  -X POST \
  -d '{
    "contents": [
      {
        "parts": [
          {
            "text": "Explain how AI works in a few words"
          }
        ]
      }
    ]
  }'
```

---

## 🎯 Future Goals

- Implement authentication and authorization with Keycloak for secure identity management.
- Add OAuth2/OpenID Connect support for SSO across microservices.
- Integrate user notification service (email/SMS/Push).
- Add more personalized AI-driven recommendations.
- Optimize microservices for Kubernetes deployment.
- Enhance API documentation and onboarding guides.

---

## 📬 Contact & Contribution

Feel free to open issues or pull requests if you'd like to contribute or need support!
