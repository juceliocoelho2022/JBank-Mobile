package com.jucelio.jbankmobile.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

private val Navy = Color(0xFF031B3A)
private val Green = Color(0xFF00C96B)
private val Background = Color(0xFFF4F6FA)
private val Muted = Color(0xFF667085)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    state: TransactionUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onFilterChange: (TransactionFilter) -> Unit,
    onHomeClick: () -> Unit,
    onAccountsClick: () -> Unit
) {
    Scaffold(
        containerColor = Background,

        topBar = {
            Column(
                modifier = Modifier.background(Navy)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Transações",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    },

                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Voltar",
                                tint = Color.White
                            )
                        }
                    },

                    actions = {
                        IconButton(onClick = onRefresh) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Atualizar",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filtrar",
                                tint = Color.White
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Navy
                    )
                )

                FilterTabs(
                    selectedFilter = state.filter,
                    onFilterChange = onFilterChange
                )
            }
        },

        bottomBar = {
            TransactionBottomBar(
                onHomeClick = onHomeClick,
                onAccountsClick = onAccountsClick
            )
        }
    ) { innerPadding ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Green)
                }
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

            else -> {
                TransactionContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    transactions = state.filteredTransactions
                )
            }
        }
    }
}

@Composable
private fun FilterTabs(
    selectedFilter: TransactionFilter,
    onFilterChange: (TransactionFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilterButton(
            text = "Todas",
            selected = selectedFilter == TransactionFilter.ALL,
            onClick = {
                onFilterChange(TransactionFilter.ALL)
            }
        )

        FilterButton(
            text = "Entradas",
            selected = selectedFilter == TransactionFilter.INCOME,
            onClick = {
                onFilterChange(TransactionFilter.INCOME)
            }
        )

        FilterButton(
            text = "Saídas",
            selected = selectedFilter == TransactionFilter.EXPENSE,
            onClick = {
                onFilterChange(TransactionFilter.EXPENSE)
            }
        )
    }
}

@Composable
private fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (selected) {
                    Green
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            }
        )
    }
}

@Composable
private fun TransactionContent(
    modifier: Modifier,
    transactions: List<TransactionResponseDto>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 14.dp,
            end = 14.dp,
            top = 16.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (transactions.isEmpty()) {
            item {
                EmptyTransactionCard()
            }
        } else {
            items(
                items = transactions,
                key = {
                    it.id ?: it.hashCode().toLong()
                }
            ) { transaction ->
                TransactionCard(transaction)
            }
        }
    }
}

@Composable
private fun TransactionCard(
    transaction: TransactionResponseDto
) {
    val income = transaction.type
        ?.uppercase() == "DEPOSIT"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        color = transactionColor(
                            transaction.type
                        ).copy(alpha = 0.12f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = transactionIcon(
                        transaction.type
                    ),
                    contentDescription = null,
                    tint = transactionColor(
                        transaction.type
                    )
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transactionTitle(transaction),
                    color = Navy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = transaction.description
                        ?: "Operação bancária",
                    color = Muted,
                    fontSize = 12.sp
                )

                Text(
                    text = transaction.createdAt
                        ?: "",
                    color = Muted,
                    fontSize = 11.sp
                )
            }

            Text(
                text = formatValue(
                    amount = transaction.amount,
                    income = income
                ),
                color = if (income) {
                    Green
                } else {
                    Color(0xFF1F2937)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun EmptyTransactionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ReceiptLong,
                contentDescription = null,
                tint = Muted,
                modifier = Modifier.size(48.dp)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Nenhuma transação encontrada",
                color = Navy,
                fontWeight = FontWeight.Bold
            )
        }
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
        Text(
            text = message,
            color = Navy
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Atualizar",
                tint = Green,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
private fun TransactionBottomBar(
    onHomeClick: () -> Unit,
    onAccountsClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Início"
                )
            },
            label = {
                Text("Início")
            },
            colors = navigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onAccountsClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Wallet,
                    contentDescription = "Contas"
                )
            },
            label = {
                Text("Contas")
            },
            colors = navigationColors()
        )

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Transações"
                )
            },
            label = {
                Text("Transações")
            },
            colors = navigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações"
                )
            },
            label = {
                Text("Notificações")
            },
            colors = navigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            },
            colors = navigationColors()
        )
    }
}

@Composable
private fun navigationColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = Green,
        selectedTextColor = Green,
        indicatorColor = Green.copy(alpha = 0.10f),
        unselectedIconColor = Navy,
        unselectedTextColor = Navy
    )

private fun transactionTitle(
    transaction: TransactionResponseDto
): String {
    return when (transaction.type?.uppercase()) {
        "PIX" -> "PIX"
        "DEPOSIT" -> "Depósito"
        "WITHDRAW" -> "Saque"
        "TRANSFER" -> "Transferência"
        "TED" -> "TED"
        else -> transaction.type ?: "Transação"
    }
}

private fun transactionColor(
    type: String?
): Color {
    return when (type?.uppercase()) {
        "PIX" -> Green
        "DEPOSIT" -> Color(0xFF00A878)
        "WITHDRAW" -> Color(0xFFF97316)
        "TRANSFER" -> Color(0xFF6366F1)
        "TED" -> Color(0xFF2563EB)
        else -> Navy
    }
}

private fun transactionIcon(
    type: String?
): ImageVector {
    return when (type?.uppercase()) {
        "PIX" -> Icons.Default.QrCode
        "DEPOSIT" -> Icons.Default.Wallet
        "WITHDRAW" -> Icons.Default.ReceiptLong
        "TRANSFER" -> Icons.Default.SwapHoriz
        "TED" -> Icons.Default.SwapHoriz
        else -> Icons.Default.ReceiptLong
    }
}

private fun formatValue(
    amount: BigDecimal?,
    income: Boolean
): String {
    val formatted = NumberFormat
        .getCurrencyInstance(
            Locale("pt", "BR")
        )
        .format(amount ?: BigDecimal.ZERO)

    return if (income) {
        "+ $formatted"
    } else {
        "- $formatted"
    }
}