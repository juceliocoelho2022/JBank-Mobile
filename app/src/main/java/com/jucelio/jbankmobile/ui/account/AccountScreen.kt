package com.jucelio.jbankmobile.ui.account

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import com.jucelio.jbankmobile.domain.model.Account
private val Navy = Color(0xFF031B3A)
private val NavyLight = Color(0xFF082A55)
private val Green = Color(0xFF00C96B)
private val PageBackground = Color(0xFFF4F6FA)
private val MutedText = Color(0xFF667085)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    state: AccountUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onAddAccount: () -> Unit = {},
    onAccountClick: (Long) -> Unit = {}
) {
    Scaffold(
        containerColor = PageBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Contas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
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
                    IconButton(onClick = onAddAccount) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar conta",
                            tint = Green,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Navy
                )
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

            else -> {
                AccountContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    accounts = state.accounts,
                    onAccountClick = onAccountClick
                )
            }
        }
    }
}

@Composable
private fun AccountContent(
    modifier: Modifier,
    accounts: List<Account>,
    onAccountClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 22.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Minhas contas",
                color = Navy,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        if (accounts.isEmpty()) {
            item {
                EmptyAccountCard()
            }
        } else {
            itemsIndexed(
                items = accounts,
                key = { _, account ->
                    account.id
                }
            ) { index, account ->

                AccountCard(
                    account = account,
                    isPrincipal = index == 0,
                    onClick = {
                        onAccountClick(account.id)
                    }
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Cartões",
                color = Navy,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            CreditCardSection(
                limit = accounts
                    .firstOrNull()
                    ?.balance
                    ?: BigDecimal.ZERO
            )
        }
    }
}

@Composable
private fun AccountCard(
    account: Account,
    isPrincipal: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    accountIconColor(account.type),
                                    accountIconColor(account.type)
                                        .copy(alpha = 0.65f)
                                )
                            ),
                            shape = RoundedCornerShape(13.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector =
                            Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(27.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = formatAccountType(account.type),
                        color = Navy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = maskAccountNumber(account.number),
                        color = MutedText,
                        fontSize = 14.sp
                    )
                }

                if (isPrincipal) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Green.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 6.dp
                            )
                    ) {
                        Text(
                            text = "Principal",
                            color = Color(0xFF078A49),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Saldo",
                        color = MutedText,
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = account.balance.toCurrency(),
                        color = Navy,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Abrir conta",
                    tint = MutedText,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun CreditCardSection(
    limit: BigDecimal
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFF2563EB)
                            .copy(alpha = 0.14f),
                        shape = RoundedCornerShape(13.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = null,
                    tint = Color(0xFF2563EB),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Cartão de Crédito",
                    color = Navy,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "•••• 3456",
                    color = MutedText,
                    fontSize = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "Limite disponível",
                    color = MutedText,
                    fontSize = 19.sp
                )

                Text(
                    text = limit.toCurrency(),
                    color = Navy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy((-8).dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = Color(0xFFEB001B),
                            shape = CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = Color(0xFFF79E1B),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
private fun EmptyAccountCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(48.dp)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Nenhuma conta encontrada",
                color = Navy,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Green
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
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Tentar novamente",
                tint = Green,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

private fun BigDecimal.toCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}

private fun maskAccountNumber(
    number: String
): String {
    val finalDigits = number
        .filter { it.isDigit() }
        .takeLast(4)
        .ifBlank { "0000" }

    return "•••• $finalDigits"
}

private fun formatAccountType(
    type: String
): String {
    return when (type.uppercase()) {
        "CHECKING",
        "CURRENT",
        "CONTA_CORRENTE" ->
            "Conta Corrente"

        "SAVINGS",
        "POUPANCA",
        "CONTA_POUPANCA" ->
            "Conta Poupança"

        "INVESTMENT",
        "INVESTIMENTO",
        "CONTA_INVESTIMENTO" ->
            "Conta Investimento"

        else ->
            type.replace("_", " ")
                .lowercase()
                .replaceFirstChar {
                    it.uppercase()
                }
    }
}

private fun accountIconColor(
    type: String
): Color {
    return when (type.uppercase()) {
        "CHECKING",
        "CURRENT",
        "CONTA_CORRENTE" ->
            Color(0xFF315CF4)

        "SAVINGS",
        "POUPANCA",
        "CONTA_POUPANCA" ->
            Color(0xFF089E91)

        "INVESTMENT",
        "INVESTIMENTO",
        "CONTA_INVESTIMENTO" ->
            Color(0xFF008A76)

        else ->
            NavyLight
    }
}