package com.jucelio.jbankmobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jucelio.jbankmobile.ui.account.AccountScreen
import com.jucelio.jbankmobile.ui.account.AccountViewModel
import com.jucelio.jbankmobile.ui.dashboard.DashboardViewModel
import com.jucelio.jbankmobile.ui.delivery.DeliveryHistoryScreen
import com.jucelio.jbankmobile.ui.delivery.DeliveryHomeScreen
import com.jucelio.jbankmobile.ui.delivery.DeliveryRouteScreen
import com.jucelio.jbankmobile.ui.delivery.DeliveryScannerScreen
import com.jucelio.jbankmobile.ui.delivery.DeliveryViewModel
import com.jucelio.jbankmobile.ui.home.HomeScreen
import com.jucelio.jbankmobile.ui.login.LoginScreen
import com.jucelio.jbankmobile.ui.login.LoginViewModel
import com.jucelio.jbankmobile.ui.notification.NotificationScreen
import com.jucelio.jbankmobile.ui.notification.NotificationViewModel
import com.jucelio.jbankmobile.ui.pix.CentralPixScreen
import com.jucelio.jbankmobile.ui.pix.PixQrResultScreen
import com.jucelio.jbankmobile.ui.pix.PixQrScannerScreen
import com.jucelio.jbankmobile.ui.pix.PixScreen
import com.jucelio.jbankmobile.ui.profile.ProfileScreen
import com.jucelio.jbankmobile.ui.splash.SplashScreen
import com.jucelio.jbankmobile.ui.transaction.TransactionScreen
import com.jucelio.jbankmobile.ui.transaction.TransactionViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.jucelio.jbankmobile.ui.startup.StartupDestination
import com.jucelio.jbankmobile.ui.startup.StartupViewModel
private object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"

    const val ACCOUNTS = "accounts"
    const val CARDS = "cards"
    const val TRANSACTIONS = "transactions"
    const val NOTIFICATIONS = "notifications"
    const val PROFILE = "profile"
    const val INVESTMENTS = "investments"

    const val PIX = "pix"
    const val PIX_SEND = "pix_send"
    const val PIX_SCANNER = "pix_scanner"
    const val PIX_CONFIRM = "pix_confirm"

    // Modo entregador (RotaCerta) — grafo próprio com ViewModel compartilhado.
    const val DELIVERY_GRAPH = "delivery_graph"
    const val DELIVERY_HOME = "delivery_home"
    const val DELIVERY_ROUTE = "delivery_route"
    const val DELIVERY_HISTORY = "delivery_history"
    const val DELIVERY_SCANNER = "delivery_scanner"
}

@Composable
fun JBankApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        /*
         * ============================================================
         * SPLASH
         * ============================================================
         */

        composable(Routes.SPLASH) {

            val startupViewModel: StartupViewModel = hiltViewModel()

            val destination = startupViewModel.destination

            var splashFinished by rememberSaveable {
                mutableStateOf(false)
            }

            LaunchedEffect(
                splashFinished,
                destination
            ) {
                if (!splashFinished || destination == null) {
                    return@LaunchedEffect
                }

                val targetRoute = when (destination) {
                    StartupDestination.HOME -> Routes.HOME
                    StartupDestination.LOGIN -> Routes.LOGIN
                }

                navController.navigate(targetRoute) {
                    popUpTo(Routes.SPLASH) {
                        inclusive = true
                    }

                    launchSingleTop = true
                }
            }

            SplashScreen(
                onFinished = {
                    splashFinished = true
                }
            )
        }

        /*
         * ============================================================
         * LOGIN
         * ============================================================
         */

        composable(Routes.LOGIN) {
            val loginViewModel: LoginViewModel =
                hiltViewModel()

            LoginScreen(
                state = loginViewModel.state,

                onEmailChange =
                    loginViewModel::onEmailChange,

                onPasswordChange =
                    loginViewModel::onPasswordChange,

                onLogin = {
                    loginViewModel.login {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        /*
         * ============================================================
         * HOME
         * ============================================================
         */


            composable(Routes.HOME) {
                val homeViewModel: DashboardViewModel =
                    hiltViewModel()

                HomeScreen(
                    state = homeViewModel.state,

                    onRefresh = homeViewModel::load,

                    onLogout = {
                        homeViewModel.logout {
                            navigateToLogin(
                                navController = navController
                            )
                        }
                    },

                /*
                 * Ação rápida "Contas".
                 */
                onAccountsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.ACCOUNTS
                    )
                },

                /*
                 * Item "Cartões" da navegação inferior.
                 */
                onCardsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.CARDS
                    )
                },

                onNotificationsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.NOTIFICATIONS
                    )
                },

                onProfileClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.PROFILE
                    )
                },

                onPixClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.PIX
                    )
                },

                onTransferClick = {
                    println("Abrir tela de transferência")
                },

                onDepositClick = {
                    println("Abrir tela de depósito")
                },

                onWithdrawClick = {
                    println("Abrir tela de saque")
                },

                onInvestmentsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.INVESTMENTS
                    )
                },

                onQrCodeClick = {
                    navController.navigate(Routes.PIX_SCANNER) {
                        launchSingleTop = true
                    }
                },

                onDeliveriesClick = {
                    navController.navigate(Routes.DELIVERY_GRAPH) {
                        launchSingleTop = true
                    }
                }
            )
        }

        /*
         * ============================================================
         * CONTAS
         * ============================================================
         */

        composable(Routes.ACCOUNTS) {
            val accountViewModel: AccountViewModel = hiltViewModel()

            AccountScreen(
                state = accountViewModel.state,

                onBack = {
                    navController.popBackStack()
                },

                onRefresh = accountViewModel::loadAccounts,

                onAddAccount = {
                    println("Abrir cadastro de nova conta")
                },

                onAccountClick = { accountId ->
                    println("Conta selecionada: $accountId")
                }
            )
        }

        /*
         * ============================================================
         * CARTÕES
         * ============================================================
         *
         * Esta tela é provisória.
         * Depois ela será substituída pela CardsScreen premium.
         */

        composable(Routes.CARDS) {
            CardsPlaceholderScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        /*
         * ============================================================
         * TRANSAÇÕES
         * ============================================================
         *
         * A rota continua existindo para:
         * - últimas movimentações;
         * - extrato PIX;
         * - botão "Ver todas";
         * - histórico bancário.
         */

        composable(Routes.TRANSACTIONS) {

            val transactionViewModel: TransactionViewModel =
                hiltViewModel()

            TransactionScreen(
                state = transactionViewModel.state,

                onBack = {
                    navController.popBackStack()
                },

                onRefresh = transactionViewModel::loadTransactions,

                onFilterChange = transactionViewModel::changeFilter,

                onHomeClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.HOME
                    )
                },

                onAccountsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.ACCOUNTS
                    )
                }
            )
        }

        /*
         * ============================================================
         * NOTIFICAÇÕES
         * ============================================================
         */

        composable(Routes.NOTIFICATIONS) {
            val notificationViewModel: NotificationViewModel =
                hiltViewModel()

            NotificationScreen(
                state = notificationViewModel.state,

                onBack = {
                    navController.popBackStack()
                },

                onRefresh = notificationViewModel::loadNotifications,

                onHomeClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.HOME
                    )
                },

                onAccountsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.ACCOUNTS
                    )
                },

                onTransactionsClick = {
                    navController.navigate(Routes.TRANSACTIONS) {
                        launchSingleTop = true
                    }
                }
            )
        }

        /*
         * ============================================================
         * CENTRAL PIX
         * ============================================================
         */

        composable(Routes.PIX) {
            CentralPixScreen(
                onBack = {
                    navController.popBackStack()
                },

                onSendPixClick = {
                    navController.navigate(Routes.PIX_SEND) {
                        launchSingleTop = true
                    }
                },

                onReadQrCodeClick = {
                    navController.navigate(Routes.PIX_SCANNER) {
                        launchSingleTop = true
                    }
                },

                onCopyPasteClick = {
                    println("Abrir PIX Copia e Cola")
                },

                onContactPixClick = {
                    navController.navigate(Routes.PIX_SEND) {
                        launchSingleTop = true
                    }
                },

                onGiftPixClick = {
                    println("Abrir PIX de presente")
                },

                onCreateQrCodeClick = {
                    println("Criar QR Code para receber PIX")
                },

                onPixKeysClick = {
                    println("Abrir Minhas Chaves PIX")
                },

                onStatementClick = {
                    navController.navigate(Routes.TRANSACTIONS) {
                        launchSingleTop = true
                    }
                },

                onChargesClick = {
                    println("Abrir cobranças PIX")
                },

                onScheduledPixClick = {
                    println("Abrir PIX agendado")
                },

                onReportFraudClick = {
                    println("Abrir contestação de PIX")
                },

                onHomeClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.HOME
                    )
                },

                onCardsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.CARDS
                    )
                },

                onInvestmentsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.INVESTMENTS
                    )
                },

                onProfileClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.PROFILE
                    )
                }
            )
        }

        /*
         * ============================================================
         * ENVIAR PIX
         * ============================================================
         */

        composable(Routes.PIX_SEND) {
            PixScreen(
                onBack = {
                    navController.popBackStack()
                },

                onContinue = {
                        keyType,
                        pixKey,
                        amount,
                        description ->

                    println(
                        """
                        PIX
                        Tipo: $keyType
                        Chave: $pixKey
                        Valor: $amount
                        Descrição: $description
                        """.trimIndent()
                    )
                }
            )
        }

        /*
         * ============================================================
         * SCANNER DE QR CODE PIX
         * ============================================================
         */

        composable(Routes.PIX_SCANNER) {
            PixQrScannerScreen(
                onBack = {
                    navController.popBackStack()
                },

                onQrCodeRead = { qrCodeValue ->
                    navController
                        .currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            key = "pixQrCode",
                            value = qrCodeValue
                        )

                    println(
                        "QR Code detectado: $qrCodeValue"
                    )

                    navController.navigate(Routes.PIX_CONFIRM) {
                        launchSingleTop = true
                    }
                }
            )
        }

        /*
         * ============================================================
         * RESULTADO DO QR CODE
         * ============================================================
         */

        composable(Routes.PIX_CONFIRM) {
            val qrCodeValue = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("pixQrCode")
                .orEmpty()

            PixQrResultScreen(
                qrCodeValue = qrCodeValue,

                onBack = {
                    navController.popBackStack()
                },

                onContinue = {
                    println(
                        "Continuar com QR Code: $qrCodeValue"
                    )
                }
            )
        }

        /*
         * ============================================================
         * ROTA DE ENTREGAS (iFood / Encomendas)
         * ============================================================
         */

        navigation(
            route = Routes.DELIVERY_GRAPH,
            startDestination = Routes.DELIVERY_HOME
        ) {

            /*
             * INÍCIO DO ENTREGADOR
             */
            composable(Routes.DELIVERY_HOME) { backStackEntry ->
                val deliveryViewModel =
                    sharedDeliveryViewModel(navController, backStackEntry)

                DeliveryHomeScreen(
                    state = deliveryViewModel.state,

                    onToggleOnline = deliveryViewModel::toggleOnline,

                    onScanClick = {
                        navController.navigate(Routes.DELIVERY_SCANNER) {
                            launchSingleTop = true
                        }
                    },

                    onRouteClick = {
                        navController.navigate(Routes.DELIVERY_ROUTE) {
                            launchSingleTop = true
                        }
                    },

                    onHistoryClick = {
                        navController.navigate(Routes.DELIVERY_HISTORY) {
                            launchSingleTop = true
                        }
                    },

                    onProfileClick = {
                        navigateToMainRoute(
                            navController = navController,
                            route = Routes.PROFILE
                        )
                    }
                )
            }

            /*
             * ROTA DE ENTREGAS (iFood / Encomendas)
             */
            composable(Routes.DELIVERY_ROUTE) { backStackEntry ->
                val deliveryViewModel =
                    sharedDeliveryViewModel(navController, backStackEntry)

                DeliveryRouteScreen(
                    state = deliveryViewModel.state,
                    onBack = { navController.popBackStack() },
                    onScanClick = {
                        navController.navigate(Routes.DELIVERY_SCANNER) {
                            launchSingleTop = true
                        }
                    },
                    onReorder = deliveryViewModel::reorderRoute,
                    onClear = deliveryViewModel::clearRoute,
                    onMarkDelivered = deliveryViewModel::markAsDelivered,
                    onMarkFailed = deliveryViewModel::markAsFailed,
                    onReopen = deliveryViewModel::reopen,
                    onRemove = deliveryViewModel::removeStop,
                    onMessageShown = deliveryViewModel::consumeMessage
                )
            }

            /*
             * HISTÓRICO
             */
            composable(Routes.DELIVERY_HISTORY) { backStackEntry ->
                val deliveryViewModel =
                    sharedDeliveryViewModel(navController, backStackEntry)

                DeliveryHistoryScreen(
                    state = deliveryViewModel.state,
                    onScanClick = {
                        navController.navigate(Routes.DELIVERY_SCANNER) {
                            launchSingleTop = true
                        }
                    },
                    onHomeClick = { navController.popBackStack(Routes.DELIVERY_HOME, false) },
                    onRouteClick = {
                        navController.navigate(Routes.DELIVERY_ROUTE) {
                            launchSingleTop = true
                        }
                    },
                    onProfileClick = {
                        navigateToMainRoute(
                            navController = navController,
                            route = Routes.PROFILE
                        )
                    }
                )
            }

            /*
             * SCANNER DE ENDEREÇO — adiciona à rota via ViewModel compartilhado
             */
            composable(Routes.DELIVERY_SCANNER) { backStackEntry ->
                val deliveryViewModel =
                    sharedDeliveryViewModel(navController, backStackEntry)

                DeliveryScannerScreen(
                    onBack = { navController.popBackStack() },
                    onCodeRead = { code ->
                        deliveryViewModel.addFromScannedCode(code)
                        navController.popBackStack()
                    }
                )
            }
        }

        /*
         * ============================================================
         * INVESTIMENTOS
         * ============================================================
         */

        composable(Routes.INVESTMENTS) {
            InvestmentsPlaceholderScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        /*
         * ============================================================
         * PERFIL
         * ============================================================
         */

        composable(Routes.PROFILE) {
            val profileViewModel: DashboardViewModel =
                hiltViewModel()

            ProfileScreen(
                userName = "Jucelio Farias Coelho",
                userEmail = "admin@jbank.com",

                onHomeClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.HOME
                    )
                },

                /*
                 * Mantido porque a assinatura atual do ProfileScreen
                 * ainda possui onAccountsClick.
                 */
                onAccountsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.ACCOUNTS
                    )
                },

                /*
                 * O item que antes se chamava Transações
                 * agora abre a página de Cartões.
                 */
                onTransactionsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.CARDS
                    )
                },

                onNotificationsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.NOTIFICATIONS
                    )
                },

                onLogout = {
                    profileViewModel.logout {
                        navigateToLogin(
                            navController = navController
                        )
                    }
                },
                onMyProfileClick = {
                    println("Abrir dados pessoais")
                },

                onSecurityClick = {
                    println("Abrir segurança")
                },

                onCardsClick = {
                    navigateToMainRoute(
                        navController = navController,
                        route = Routes.CARDS
                    )
                },

                onBeneficiariesClick = {
                    println("Abrir beneficiários")
                },

                onSettingsClick = {
                    println("Abrir configurações")
                },

                onHelpClick = {
                    println("Abrir ajuda e suporte")
                },

                onAboutClick = {
                    println("Abrir sobre o JBank")
                }
            )
        }
    }
}

/*
 * ================================================================
 * TELA PROVISÓRIA DE CARTÕES
 * ================================================================
 */

@Composable
private fun CardsPlaceholderScreen(
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF060712)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CreditCard,
                contentDescription = "Cartões",
                tint = Color(0xFFB45CFF)
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Cartões JBank",
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "A página premium de cartões será criada nesta rota.",
                color = Color(0xFFB6B1C2),
                fontSize = 15.sp,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(26.dp)
            )

            Button(
                onClick = onBack
            ) {
                Text(
                    text = "Voltar"
                )
            }
        }
    }
}

/*
 * ================================================================
 * TELA PROVISÓRIA DE INVESTIMENTOS
 * ================================================================
 */

@Composable
private fun InvestmentsPlaceholderScreen(
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF060712)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShowChart,
                contentDescription = "Investimentos",
                tint = Color(0xFFB45CFF)
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Investimentos",
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "A página de investimentos será criada nesta rota.",
                color = Color(0xFFB6B1C2),
                fontSize = 15.sp,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(26.dp)
            )

            Button(
                onClick = onBack
            ) {
                Text(
                    text = "Voltar"
                )
            }
        }
    }
}

/*
 * ================================================================
 * FUNÇÕES AUXILIARES DE NAVEGAÇÃO
 * ================================================================
 */

/**
 * Resolve o [DeliveryViewModel] compartilhado por todas as telas do
 * modo entregador, escopo do grafo [Routes.DELIVERY_GRAPH]. Assim
 * Início, Rota, Histórico e Scanner enxergam a mesma rota em memória.
 */
@Composable
private fun sharedDeliveryViewModel(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry
): DeliveryViewModel {
    val graphEntry = remember(backStackEntry) {
        navController.getBackStackEntry(Routes.DELIVERY_GRAPH)
    }
    return hiltViewModel(graphEntry)
}

private fun navigateToMainRoute(
    navController: NavHostController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(Routes.HOME) {
            inclusive = false
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToLogin(
    navController: NavHostController
) {
    navController.navigate(Routes.LOGIN) {
        popUpTo(0) {
            inclusive = true
        }

        launchSingleTop = true
    }
}