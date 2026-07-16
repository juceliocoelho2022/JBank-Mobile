<div align="center">

<img src="https://github.com/user-attachments/assets/05037798-7e11-4962-8f33-5a789f85cf58" width="70%">

# 🏦 JBank Mobile

### Modern Digital Banking Application built with Kotlin & Jetpack Compose

A modern Android banking application inspired by Brazil's leading digital banks, developed using **Kotlin**, **Jetpack Compose**, **Material Design 3**, **MVVM Architecture**, and secure **JWT Authentication**.

---

![Android](https://img.shields.io/badge/Android-35-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-orange)
![Retrofit](https://img.shields.io/badge/Networking-Retrofit-red)
![JWT](https://img.shields.io/badge/Security-JWT-success)
![Spring Boot](https://img.shields.io/badge/API-Spring%20Boot-6DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

<img src="https://img.shields.io/github/stars/SEU-USUARIO/JBank-Mobile?style=social">
<img src="https://img.shields.io/github/forks/SEU-USUARIO/JBank-Mobile?style=social">

</div>

---

# 📖 Table of Contents

- About
- Features
- Screenshots
- Demo
- Tech Stack
- Architecture
- Project Structure
- Authentication Flow
- API Communication
- Installation
- Running the Project
- Configuration
- Roadmap
- Skills Demonstrated
- Future Improvements
- Contributing
- Developer
- License

---

# 📱 About

JBank Mobile is a modern Android Banking Application created to demonstrate enterprise-level Android development.

The application communicates with the **JBank REST API**, responsible for authentication, account management, dashboard information, banking transactions and future financial services.

This project follows modern Android best practices, emphasizing scalability, clean architecture, maintainability and security.

Inspired by applications such as:

- Nubank
- Itaú
- Inter
- C6 Bank
- PicPay

---

# ✨ Highlights

✔ Kotlin 2.0

✔ Jetpack Compose

✔ Material Design 3

✔ MVVM Architecture

✔ Repository Pattern

✔ REST API Integration

✔ JWT Authentication

✔ Secure Token Storage

✔ DataStore

✔ Retrofit

✔ OkHttp

✔ Coroutines

✔ StateFlow

✔ Navigation Compose

✔ Clean Code

✔ Android Architecture Components

✔ Responsive UI

✔ Production-ready structure

---

# 🚀 Features

## Authentication

- Login
- Logout
- JWT Authentication
- Bearer Token
- Automatic Token Injection
- Session Persistence

---

## Dashboard

- Welcome Screen
- Account Information
- Balance
- Quick Actions

---

## Architecture

- MVVM
- Repository Pattern
- Reactive UI
- StateFlow
- ViewModel
- Single Source of Truth

---

## Networking

- Retrofit
- OkHttp
- Gson
- HTTP Interceptor
- Error Handling
- Timeout Configuration

---

## Security

- JWT
- Secure Authentication
- Protected Routes
- Persistent Login
- Authorization Header

---

# 📸 Screenshots

> Replace these placeholders with real screenshots.

| Login | Dashboard |
|--------|-----------|
| ![](docs/login.png) | ![](docs/dashboard.png) |

| Profile | Transactions |
|----------|--------------|
| ![](docs/profile.png) | ![](docs/transactions.png) |

---

# 🎥 Demo

Coming Soon

Example:

https://github.com/user-attachments/assets/demo-video.mp4

---

# 🛠 Tech Stack

| Layer | Technology |
|--------|------------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose |
| Design | Material Design 3 |
| Architecture | MVVM |
| Navigation | Navigation Compose |
| Async | Coroutines |
| State | StateFlow |
| Networking | Retrofit |
| HTTP Client | OkHttp |
| Serialization | Gson |
| Authentication | JWT |
| Local Storage | DataStore |
| Backend | Spring Boot |
| Database | PostgreSQL |
| Build Tool | Gradle Kotlin DSL |
| IDE | Android Studio Koala |

---

# 🏗 Architecture

```

Presentation
│
├── Compose UI
├── Screens
├── Components
└── ViewModels
│
▼
Domain
│
├── Models
├── Use Cases
└── Business Rules
│
▼
Data
│
├── Repository
├── Retrofit
├── DTOs
├── API
└── DataStore
│
▼
JBank REST API
│
▼
Spring Boot
│
▼
PostgreSQL

```

---

# 📂 Project Structure

```

app
│
├── data
│ ├── api
│ ├── dto
│ ├── repository
│ ├── datastore
│ └── mapper
│
├── domain
│ ├── model
│ ├── repository
│ └── usecase
│
├── ui
│ ├── login
│ ├── dashboard
│ ├── profile
│ ├── navigation
│ ├── components
│ └── theme
│
├── utils
│
└── MainActivity.kt

```

---

# 🔐 Authentication Flow

```

User Login
│
▼
JBank API
│
▼
JWT Token
│
▼
DataStore
│
▼
Authorization Bearer
│
▼
Authenticated Requests

```

---

# 🌐 API Communication

```

Android App

↓

Retrofit

↓

OkHttp Interceptor

↓

JBank REST API

↓

Spring Boot

↓

PostgreSQL

```

---

# 📦 Main Dependencies

```kotlin
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose")

implementation("com.squareup.retrofit2:retrofit")
implementation("com.squareup.retrofit2:converter-gson")

implementation("com.squareup.okhttp3:okhttp")
implementation("com.squareup.okhttp3:logging-interceptor")

implementation("androidx.datastore:datastore-preferences")

implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
```
