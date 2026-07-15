package com.jucelio.jbankmobile.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.ui.dashboard.DashboardUiState
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

private val Background = Color(0xFF060712)
private val Surface = Color(0xFF111322)
private val SurfaceLight = Color(0xFF181A2E)

private val Purple = Color(0xFF8B2CFF)
private val PurpleLight = Color(0xFFB45CFF)
private val DeepPurple = Color(0xFF4C0DB8)

private val Green = Color(0xFF35E36F)
private val Red = Color(0xFFFF5C73)

private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFA8A5B7)
private val DividerColor = Color(0xFF292B3D)

@Composable
fun HomeScreen(
    state: DashboardUiState,
    onRefresh: () -> Unit,
    onLogout: () -> Unit,
    onAccountsClick: () -> Unit,
    onCardsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onPixClick: () -> Unit,
    onTransferClick: () -> Unit,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onInvestmentsClick: () -> Unit = {},
    onQrCodeClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,

        bottomBar = {
            HomeBottomNavigation(
                onPixClick = onPixClick,
                onCardsClick = onCardsClick,
                onInvestmentsClick = onInvestmentsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        when {
            state.isLoading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            state.errorMessage != null -> {
                ErrorContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = state.errorMessage,
                    onRefresh = onRefresh
                )
            }

            state.data == null -> {
                ErrorContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = "Não foi possível carregar os dados.",
                    onRefresh = onRefresh
                )
            }

            else -> {
                val dashboard = state.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 18.dp,
                        bottom = 28.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    item {
                        HomeHeader(
                            onNotificationsClick = onNotificationsClick,
                            onQrCodeClick = onQrCodeClick
                        )
                    }

                    item {
                        BalanceCard(
                            totalBalance = dashboard.totalBalance
                        )
                    }

                    item {
                        SectionTitle(
                            title = "Ações rápidas"
                        )
                    }

                    item {
                        QuickActionsGrid(
                            onPixClick = onPixClick,
                            onTransferClick = onTransferClick,
                            onDepositClick = onDepositClick,
                            onWithdrawClick = onWithdrawClick,
                            onInvestmentsClick = onInvestmentsClick,
                            onAccountsClick = onAccountsClick,
                            onCardsClick = onCardsClick
                        )
                    }

                    item {
                        LoanBanner()
                    }

                    item {
                        TransactionsCard(
                            transactions = dashboard.latestTransactions,
                            onSeeAllClick = onAccountsClick
                        )
                    }

                    item {
                        MonthlySummaryCard(
                            totalMoved = dashboard.totalMoved
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    onNotificationsClick: () -> Unit,
    onQrCodeClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Olá, Jucelio 👋",
                color = WhiteText,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Que bom ter você de volta!",
                color = SecondaryText,
                fontSize = 14.sp
            )
        }

        BadgedBox(
            badge = {
                Badge(
                    containerColor = Red
                ) {
                    Text(
                        text = "2",
                        color = Color.White,
                        fontSize = 9.sp
                    )
                }
            }
        ) {
            IconButton(
                onClick = onNotificationsClick
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = WhiteText,
                    modifier = Modifier.size(27.dp)
                )
            }
        }

        IconButton(
            onClick = onQrCodeClick
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = "Ler QR Code",
                tint = WhiteText,
                modifier = Modifier.size(27.dp)
            )
        }
    }
}

@Composable
private fun BalanceCard(
    totalBalance: BigDecimal?
) {
    var balanceVisible by remember {
        mutableStateOf(true)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Purple,
                            DeepPurple,
                            Color(0xFF32106C)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saldo em conta",
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(
                            alpha = 0.88f
                        ),
                        fontSize = 14.sp
                    )

                    IconButton(
                        onClick = {
                            balanceVisible = !balanceVisible
                        },
                        modifier = Modifier.size(35.dp)
                    ) {
                        Icon(
                            imageVector = if (balanceVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = "Mostrar ou ocultar saldo",
                            tint = Color.White
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = if (balanceVisible) {
                        totalBalance.toCurrency()
                    } else {
                        "R$ ••••••"
                    },
                    color = Color.White,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = "Conta •••• 1234-5",
                    color = Color.White.copy(
                        alpha = 0.82f
                    ),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String
) {
    Text(
        text = title,
        color = WhiteText,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun QuickActionsGrid(
    onPixClick: () -> Unit,
    onTransferClick: () -> Unit,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onInvestmentsClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onCardsClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            17.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickAction(
                icon = Icons.Default.QrCode,
                label = "PIX",
                onClick = onPixClick
            )

            QuickAction(
                icon = Icons.Default.Payments,
                label = "Pagar",
                onClick = onTransferClick
            )

            QuickAction(
                icon = Icons.Default.SwapHoriz,
                label = "Transferir",
                onClick = onTransferClick
            )

            QuickAction(
                icon = Icons.Default.AddCard,
                label = "Depositar",
                onClick = onDepositClick
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickAction(
                icon = Icons.Default.LocalAtm,
                label = "Sacar",
                onClick = onWithdrawClick
            )

            QuickAction(
                icon = Icons.Default.AccountBalanceWallet,
                label = "Contas",
                onClick = onAccountsClick
            )

            QuickAction(
                icon = Icons.Default.CreditCard,
                label = "Cartões",
                onClick = onCardsClick
            )

            QuickAction(
                icon = Icons.Default.ShowChart,
                label = "Investir",
                onClick = onInvestmentsClick
            )
        }
    }
}

@Composable
private fun RowScope.QuickAction(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(
                    RoundedCornerShape(17.dp)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Purple.copy(alpha = 0.30f),
                            DeepPurple.copy(alpha = 0.18f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = PurpleLight,
                modifier = Modifier.size(27.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = label,
            color = WhiteText,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
private fun LoanBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF401080),
                            Color(0xFF6A19C8),
                            Color(0xFF32106C)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Empréstimo pessoal",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = "Taxas especiais para realizar seus sonhos.",
                        color = Color.White.copy(
                            alpha = 0.80f
                        ),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )

                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(18.dp)
                            )
                            .background(
                                Color.White.copy(
                                    alpha = 0.16f
                                )
                            )
                            .clickable { }
                            .padding(
                                horizontal = 15.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(
                            text = "Simular agora",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(95.dp)
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                        .background(
                            Purple.copy(alpha = 0.20f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = PurpleLight,
                        modifier = Modifier.size(61.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionsCard(
    transactions: List<TransactionResponseDto>,
    onSeeAllClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Column(
            modifier = Modifier.padding(17.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Últimas movimentações",
                    modifier = Modifier.weight(1f),
                    color = WhiteText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Ver todas",
                    color = PurpleLight,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable(
                        onClick = onSeeAllClick
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (transactions.isEmpty()) {
                EmptyTransactions()
            } else {
                val visibleTransactions = transactions.take(4)

                visibleTransactions.forEachIndexed {
                        index,
                        transaction ->

                    TransactionRow(
                        transaction = transaction
                    )

                    if (
                        index < visibleTransactions.lastIndex
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(
                                start = 52.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            ),
                            color = DividerColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(
    transaction: TransactionResponseDto
) {
    val transactionType = transaction.type
        ?.uppercase()
        .orEmpty()

    val incoming = transactionType.contains(
        "DEPOSIT"
    ) || transactionType.contains(
        "RECEIVED"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(
                    if (incoming) {
                        Green.copy(alpha = 0.15f)
                    } else {
                        Purple.copy(alpha = 0.18f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = transactionIcon(
                    transaction.type
                ),
                contentDescription = null,
                tint = if (incoming) {
                    Green
                } else {
                    PurpleLight
                },
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(11.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transactionTitle(
                    transaction
                ),
                color = WhiteText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = transaction.description
                    ?: "Movimentação bancária",
                color = SecondaryText,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = transactionAmount(
                    transaction
                ),
                color = if (incoming) {
                    Green
                } else {
                    WhiteText
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = "Hoje",
                color = SecondaryText,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun EmptyTransactions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ReceiptLong,
            contentDescription = null,
            tint = PurpleLight,
            modifier = Modifier.size(38.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Nenhuma movimentação encontrada",
            color = SecondaryText,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun MonthlySummaryCard(
    totalMoved: BigDecimal?
) {
    val moved = totalMoved ?: BigDecimal.ZERO

    val income = moved.multiply(
        BigDecimal("0.63")
    )

    val expenses = moved.multiply(
        BigDecimal("0.37")
    )

    val savings = income
        .subtract(expenses)
        .max(BigDecimal.ZERO)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Column(
            modifier = Modifier.padding(17.dp)
        ) {
            Text(
                text = "Resumo do mês",
                color = WhiteText,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SummaryColumn(
                    title = "Receitas",
                    value = income.toCurrency(),
                    valueColor = Green
                )

                SummaryColumn(
                    title = "Despesas",
                    value = expenses.toCurrency(),
                    valueColor = Red
                )

                SummaryColumn(
                    title = "Economia",
                    value = savings.toCurrency(),
                    valueColor = PurpleLight
                )
            }

            Spacer(
                modifier = Modifier.height(17.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.45f)
                        .fillMaxSize()
                        .background(Green)
                )

                Box(
                    modifier = Modifier
                        .weight(0.30f)
                        .fillMaxSize()
                        .background(Red)
                )

                Box(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxSize()
                        .background(Purple)
                )
            }
        }
    }
}

@Composable
private fun RowScope.SummaryColumn(
    title: String,
    value: String,
    valueColor: Color
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = SecondaryText,
            fontSize = 11.sp
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = value,
            color = valueColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HomeBottomNavigation(
    onPixClick: () -> Unit,
    onCardsClick: () -> Unit,
    onInvestmentsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = SurfaceLight
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Início"
                )
            },
            label = {
                Text("Início")
            },
            colors = homeNavigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onPixClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "PIX"
                )
            },
            label = {
                Text("PIX")
            },
            colors = homeNavigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onCardsClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = "Cartões"
                )
            },
            label = {
                Text("Cartões")
            },
            colors = homeNavigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onInvestmentsClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Investir"
                )
            },
            label = {
                Text("Investir")
            },
            colors = homeNavigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            },
            colors = homeNavigationColors()
        )
    }
}

@Composable
private fun homeNavigationColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = PurpleLight,
        selectedTextColor = PurpleLight,
        indicatorColor = Purple.copy(
            alpha = 0.18f
        ),
        unselectedIconColor = SecondaryText,
        unselectedTextColor = SecondaryText
    )

@Composable
private fun LoadingContent(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = PurpleLight
        )
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier,
    message: String,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountBalance,
            contentDescription = null,
            tint = PurpleLight,
            modifier = Modifier.size(58.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = message,
            color = WhiteText,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = "Tentar novamente",
            color = PurpleLight,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(
                onClick = onRefresh
            )
        )
    }
}

private fun BigDecimal?.toCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(
        Locale("pt", "BR")
    )

    return formatter.format(
        this ?: BigDecimal.ZERO
    )
}

private fun transactionTitle(
    transaction: TransactionResponseDto
): String {
    return when (
        transaction.type
            ?.uppercase()
            .orEmpty()
    ) {
        "PIX" -> "PIX realizado"
        "PIX_RECEIVED" -> "PIX recebido"
        "PIX_SENT" -> "PIX enviado"
        "DEPOSIT" -> "Depósito recebido"
        "WITHDRAW" -> "Saque"
        "TRANSFER" -> "Transferência enviada"
        "TED" -> "TED realizada"
        else -> transaction.type
            ?: "Movimentação"
    }
}

private fun transactionAmount(
    transaction: TransactionResponseDto
): String {
    val value = transaction.amount.toCurrency()

    val type = transaction.type
        ?.uppercase()
        .orEmpty()

    val incoming = type.contains(
        "DEPOSIT"
    ) || type.contains(
        "RECEIVED"
    )

    return if (incoming) {
        "+ $value"
    } else {
        "- $value"
    }
}

private fun transactionIcon(
    type: String?
): ImageVector {
    return when (
        type?.uppercase().orEmpty()
    ) {
        "PIX",
        "PIX_RECEIVED",
        "PIX_SENT" -> Icons.Default.QrCode

        "DEPOSIT" -> Icons.Default.AddCard

        "WITHDRAW" -> Icons.Default.Paid

        "TRANSFER" -> Icons.Default.SwapHoriz

        "TED" -> Icons.Default.AttachMoney

        else -> Icons.Default.ReceiptLong
    }
}