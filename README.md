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
---

# ⚙ Requirements

Before running the application, make sure your environment meets the following requirements:

| Requirement | Version |
|-------------|---------|
| Android Studio | Koala or newer |
| JDK | 17+ |
| Android SDK | 35 |
| Gradle | 8.9+ |
| Kotlin | 2.0 |
| Git | Latest |

---

# 🚀 Getting Started

## Clone the Repository

```bash
git clone https://github.com/SEU-USUARIO/JBank-Mobile.git
```

Open the project in **Android Studio** and wait for the Gradle synchronization to finish.

---

# ▶ Running the Application

1. Start the **JBank API**.
2. Open Android Studio.
3. Sync Gradle.
4. Connect an Android device or emulator.
5. Click **Run**.

---

# 🌐 API Configuration

### Android Emulator

```
http://10.0.2.2:8081
```

### Physical Device

```
http://YOUR_COMPUTER_IP:8081
```

Example

```
http://192.168.0.100:8081
```

Both devices must be connected to the same network.

---

# 🔑 Authentication

Use a user previously created in the JBank API.

Example

Email

```
user@email.com
```

Password

```
********
```

---

# 📡 API Endpoints

| Endpoint | Description |
|----------|-------------|
| POST /api/auth/login | Authenticate user |
| GET /api/dashboard | Dashboard information |
| GET /api/accounts | User accounts |
| POST /api/pix | PIX transfer |
| POST /api/transfer | Bank transfer |
| GET /api/transactions | Statement |
| GET /api/profile | User profile |

---

# 📈 Roadmap

## Version 1.0

- [x] Login
- [x] Logout
- [x] JWT Authentication
- [x] Retrofit Integration
- [x] Dashboard
- [x] DataStore
- [x] Material Design 3
- [x] Navigation Compose

---

## Version 1.1

- [ ] PIX
- [ ] Transfer
- [ ] TED
- [ ] Transaction History
- [ ] User Profile

---

## Version 1.2

- [ ] Credit Card
- [ ] Debit Card
- [ ] QR Code PIX
- [ ] Notifications
- [ ] Investments

---

## Version 2.0

- [ ] Face ID
- [ ] Fingerprint Authentication
- [ ] Open Finance
- [ ] Push Notifications
- [ ] Spending Analytics
- [ ] Dark Theme
- [ ] Multi-language Support

---

# 📊 Project Metrics

| Metric | Status |
|---------|--------|
| Kotlin | ✅ |
| MVVM | ✅ |
| REST API | ✅ |
| JWT | ✅ |
| Retrofit | ✅ |
| Compose | ✅ |
| Material 3 | ✅ |
| StateFlow | ✅ |
| Coroutines | ✅ |
| DataStore | ✅ |

---

# 💼 Skills Demonstrated

This project demonstrates practical experience with:

- Android Development
- Kotlin
- Jetpack Compose
- MVVM Architecture
- Repository Pattern
- REST APIs
- JSON Serialization
- Retrofit
- OkHttp
- JWT Authentication
- Coroutines
- StateFlow
- DataStore
- Material Design 3
- Clean Code
- SOLID Principles
- Git
- GitHub
- Mobile Architecture
- Software Engineering Best Practices

---

# 🏆 Why this Project?

Unlike simple CRUD examples, JBank Mobile demonstrates how a professional Android application can be built using modern development practices.

Key aspects include:

- Production-ready architecture
- Secure authentication
- Token persistence
- Reactive UI
- Separation of concerns
- Clean and scalable codebase
- Enterprise-level organization

---

# 🎯 Learning Goals

The main purpose of this project is to:

- Learn modern Android development
- Apply MVVM Architecture
- Build scalable mobile applications
- Integrate with REST APIs
- Practice Clean Code
- Demonstrate software engineering skills
- Create a professional portfolio project

---

# 📚 Future Improvements

- Unit Tests
- UI Tests
- Dependency Injection (Hilt/Koin)
- Room Database
- Offline Mode
- Push Notifications
- Biometric Authentication
- Open Finance Integration
- Charts and Analytics
- AI Financial Assistant

---

# 🤝 Contributing

Contributions are welcome!

If you'd like to improve this project:

1. Fork this repository.
2. Create a feature branch.
3. Commit your changes.
4. Push the branch.
5. Open a Pull Request.

---

# 🐛 Reporting Issues

Found a bug?

Please open an Issue describing:

- Expected behavior
- Actual behavior
- Android version
- Device model
- Screenshots (if applicable)

---

# 📜 License

This project is licensed under the **MIT License**.

Feel free to use it for learning and educational purposes.

---

# 👨‍💻 Developer

## Jucelio Farias Coelho

Android Developer • Backend Java Developer • Systems Analyst • Mathematics Teacher • Data Analytics Enthusiast

### Tech Stack

- Kotlin
- Java
- Spring Boot
- Jetpack Compose
- PostgreSQL
- Docker
- Firebase
- Android
- REST APIs
- Git
- GitHub

---

# 📫 Connect with Me

LinkedIn

https://www.linkedin.com/in/SEU-LINK

GitHub

https://github.com/SEU-USUARIO

Portfolio

https://SEU-SITE.com

Email

seuemail@email.com

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.

It helps the project gain visibility and motivates future improvements.

---

<div align="center">

# ⭐ Thank You!

## JBank Mobile

Modern Android Banking Application

Built with ❤️ using Kotlin + Jetpack Compose

Designed and developed by

### Jucelio Farias Coelho

Android Developer • Java Backend Developer • Systems Analyst

---

### If you like this project, don't forget to leave a ⭐!

</div>
