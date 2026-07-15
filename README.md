# JBank Mobile — Jetpack Compose

Projeto Android completo para abrir no Android Studio e consumir a JBank API.

## O que já está pronto

- Kotlin + Jetpack Compose
- Material 3
- MVVM
- Navigation Compose
- Retrofit + Gson
- OkHttp Logging
- JWT salvo no DataStore
- Interceptor Bearer Token
- Login real em `/api/auth/login`
- Dashboard real em `/api/dashboard`
- Logout
- Tratamento de erros de rede

## Como abrir

1. Extraia o ZIP.
2. Abra o Android Studio.
3. Clique em **Open**.
4. Selecione a pasta `JBankMobile`.
5. Aguarde o Gradle Sync.
6. Use JDK 17 nas configurações do Gradle.
7. Inicie um emulador Android.
8. Execute o app.

## Antes de executar

A API Docker precisa estar funcionando em:

`http://localhost:8081`

No emulador Android o app usa:

`http://10.0.2.2:8081`

Isso já está configurado em `app/build.gradle.kts`.

## Credenciais

Use o mesmo e-mail e senha cadastrados na JBank API.

## Celular físico

Para executar em aparelho físico, substitua em `app/build.gradle.kts`:

`http://10.0.2.2:8081/`

pelo IP do computador, por exemplo:

`http://192.168.0.10:8081/`

O celular e o computador precisam estar na mesma rede Wi-Fi.

## Observação sobre Gradle

O projeto usa:

- Android Gradle Plugin 8.7.3
- Gradle compatível: 8.9
- Kotlin 2.0.21
- JDK 17

Caso o Android Studio solicite a versão do Gradle, selecione **Gradle 8.9**.
