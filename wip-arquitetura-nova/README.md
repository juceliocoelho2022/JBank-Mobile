# Migração de arquitetura (WIP — pausada)

Este diretório guarda o início de uma reescrita do app para uma arquitetura
nova (Clean Architecture + MVI: `UiEvent`/`UiState`/`UiEffect`, design system
próprio, biometria, criptografia de token, `core/di` com módulos Hilt
próprios, navegação baseada em `AppDestination`).

Ele foi movido para fora de `app/src/main/java` (e portanto fora da
compilação) porque coexistia com a arquitetura antiga usando os mesmos
nomes de classe em pacotes diferentes (`AuthRepositoryImpl`, `NetworkModule`,
`RepositoryModule`, `UseCaseModule`, `DispatcherModule`, `DataStoreModule`,
`AuthInterceptor`, `AuthApi`, `LoginViewModel`, `LoginEvent`), causando
bindings duplicados no Hilt e erros de compilação.

**Estado real na hora da pausa:** só a tela de Login estava de fato
conectada (`core/navigation/JBankNavHost.kt` → `LoginRoute`). A rota "home"
era um placeholder vazio, e as demais peças (design system, biometria,
`WelcomeScreen`, `AccessViewModel`, etc.) não estavam ligadas a lugar
nenhum. Dashboard, Contas, Extrato, Notificações, PIX e Perfil só existem
na arquitetura antiga (ativa em `app/src/main/java`).

## Retomando a migração

O app funcional de hoje usa a arquitetura antiga (`ui.JBankApp` como
NavHost, `di/*` como módulos Hilt). Para continuar a migração:

1. Mover os arquivos de volta para dentro de
   `app/src/main/java/com/jucelio/jbankmobile/...` (mesmo caminho relativo
   que tinham aqui), tela por tela — recomendo migrar uma feature de
   cada vez (ex.: primeiro Login, depois Dashboard) em vez de tudo de uma
   vez, para não recair na mesma situação de duas arquiteturas coexistindo
   pela metade.
2. Remover o equivalente antigo dessa mesma feature (ex.: ao migrar Login,
   remover `ui/login/LoginViewModel.kt` e trocar `di/NetworkModule.kt` pelo
   `core/di/NetworkModule.kt`, não manter os dois).
3. Atualizar `MainActivity.kt` para usar `JBankNavHost()` só quando o
   NavHost novo já cobrir todas as telas que o `ui.JBankApp()` cobre hoje.
4. Terminar `core/navigation/NavGraph.kt`, `Routes.kt`, `Destination.kt` e
   `BottomBarDestination.kt`, que hoje são classes vazias (stubs).
