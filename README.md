<div align="center">

# <img width="1254" height="1254" alt="imagen_principal" src="https://github.com/user-attachments/assets/05037798-7e11-4962-8f33-5a789f85cf58" />
 JBank Mobile

### Android Banking Application built with Kotlin + Jetpack Compose

Aplicação bancária moderna desenvolvida em **Kotlin** utilizando **Jetpack Compose**, arquitetura **MVVM**, autenticação **JWT** e integração completa com a **JBank REST API**.

![Android](https://img.shields.io/badge/Android-35-green?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-blue)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange)
![Retrofit](https://img.shields.io/badge/Networking-Retrofit-red)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

</div>

---

# 📱 Sobre o Projeto

O **JBank Mobile** é um aplicativo Android inspirado nas aplicações dos grandes bancos digitais brasileiros.

O objetivo é demonstrar a construção de uma aplicação profissional utilizando as principais tecnologias modernas do ecossistema Android.

O aplicativo consome a **JBank API**, responsável por autenticação, gerenciamento de contas, dashboard, transações e demais recursos financeiros.

---

# 🚀 Tecnologias Utilizadas

- Kotlin 2.0
- Jetpack Compose
- Material Design 3
- MVVM
- Navigation Compose
- ViewModel
- StateFlow
- Coroutines
- Retrofit
- Gson
- OkHttp
- DataStore
- JWT Authentication
- Dependency Injection
- Android Architecture Components

---

# 🏗 Arquitetura

```
Presentation
│
├── UI (Jetpack Compose)
├── ViewModels
│
Domain
│
├── Use Cases
│
Data
│
├── Repository
├── Retrofit
├── DTOs
└── DataStore
```

Arquitetura baseada em **MVVM**, separando responsabilidades para facilitar testes, manutenção e escalabilidade.

---

# ✨ Funcionalidades

## ✅ Implementadas

- Login
- Logout
- Dashboard
- Persistência do Token JWT
- Autenticação Bearer
- Interceptor automático
- Navegação entre telas
- Tratamento de erros HTTP
- Consumo da API REST
- Material Design 3
- Arquitetura MVVM

---

## 🚧 Próximas Funcionalidades

- Cadastro de usuários
- PIX
- Transferências
- TED
- Extrato
- Cartões
- Investimentos
- Perfil
- Notificações
- Biometria
- QR Code PIX
- Dark Mode
- Dashboard Financeiro

---

# 📂 Estrutura do Projeto

```
app
 ├── data
 │    ├── api
 │    ├── dto
 │    ├── repository
 │    └── datastore
 │
 ├── domain
 │
 ├── ui
 │    ├── login
 │    ├── dashboard
 │    ├── navigation
 │    ├── components
 │    └── theme
 │
 ├── model
 │
 └── MainActivity
```

---

# 🔐 Autenticação

A autenticação utiliza **JWT (JSON Web Token)**.

Fluxo:

```
Login
      │
      ▼
JBank API
      │
      ▼
JWT
      │
      ▼
DataStore
      │
      ▼
Bearer Token
      │
      ▼
Demais requisições
```

---

# 🌐 Configuração da API

No emulador Android:

```
http://10.0.2.2:8081/
```

Em dispositivo físico:

```
http://IP_DO_COMPUTADOR:8081/
```

Exemplo:

```
http://192.168.0.10:8081/
```

O computador e o celular devem estar conectados à mesma rede.

---

# ▶ Como Executar

## Pré-requisitos

- Android Studio Koala ou superior
- JDK 17
- Gradle 8.9
- Android SDK 35

---

## Clonar

```bash
git clone https://github.com/SEU-USUARIO/JBank-Mobile.git
```

---

## Abrir

Abra o projeto no Android Studio.

Aguarde o Gradle Sync.

Execute o aplicativo.

---

# ⚙ Configuração

A API deve estar em execução:

```
http://localhost:8081
```

O projeto já está configurado para utilizar:

```
http://10.0.2.2:8081
```

---

# 🔑 Credenciais

Utilize um usuário previamente cadastrado na JBank API.

Exemplo:

```
Email:
usuario@email.com

Senha:
********
```

---

# 📸 Screenshots

> Em breve serão adicionadas capturas das telas.

| Login | Dashboard | PIX | Cartão |
|-------|-----------|------|---------|
| 📷 | 📷 | 📷 | 📷 |

---

# 📊 Roadmap

- [x] Login
- [x] Dashboard
- [x] JWT
- [x] DataStore
- [x] Retrofit
- [x] Navigation
- [ ] PIX
- [ ] Cartões
- [ ] Extrato
- [ ] Investimentos
- [ ] Perfil
- [ ] Notificações
- [ ] Biometria
- [ ] QR Code

---

# 👨‍💻 Desenvolvedor

**Jucelio Farias Coelho**

Professor • Desenvolvedor Android • Desenvolvedor Backend Java • Analista de Sistemas • Ciência de Dados

### Tecnologias

- Kotlin
- Java
- Spring Boot
- PostgreSQL
- Docker
- Android
- Jetpack Compose
- Firebase
- Git
- GitHub

---

# ⭐ Objetivo

Este projeto faz parte do meu portfólio profissional e demonstra conhecimentos em:

- Desenvolvimento Android Nativo
- Arquitetura MVVM
- Clean Code
- Integração com APIs REST
- Autenticação JWT
- Persistência Local
- Boas práticas de engenharia de software

---

<div align="center">

### ⭐ Se este projeto foi útil, deixe uma Star no repositório!

**JBank Mobile**
Desenvolvido com ❤️ utilizando Kotlin + Jetpack Compose.

</div>
