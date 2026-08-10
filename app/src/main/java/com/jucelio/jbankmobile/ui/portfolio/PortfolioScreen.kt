package com.jucelio.jbankmobile.ui.portfolio

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jucelio.jbankmobile.domain.model.Investment
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

private val Background = Color(0xFF060712)
private val Surface = Color(0xFF111322)

private val Purple = Color(0xFF8B2CFF)
private val PurpleLight = Color(0xFFB45CFF)
private val DeepPurple = Color(0xFF4C0DB8)

private val Green = Color(0xFF35E36F)
private val Red = Color(0xFFFF5C73)
private val Blue = Color(0xFF5487FF)
private val Orange = Color(0xFFFFB020)

private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFA8A5B7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(
    state: PortfolioUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        containerColor = Background,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Investimentos",
                        color = WhiteText,
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = WhiteText
                        )
                    }
                },

                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Atualizar",
                            tint = PurpleLight
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
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
                PortfolioContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    investments = state.investments
                )
            }
        }
    }
}

@Composable
private fun PortfolioContent(
    modifier: Modifier,
    investments: List<Investment>
) {
    val totalInvested = investments.sumOf(Investment::investedAmount)
    val totalCurrent = investments.sumOf(Investment::currentValue)
    val totalProfit = totalCurrent.subtract(totalInvested)

    val totalProfitPercent = if (totalInvested > BigDecimal.ZERO) {
        totalProfit
            .divide(totalInvested, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal(100))
    } else {
        BigDecimal.ZERO
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 20.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            PortfolioSummaryCard(
                totalCurrent = totalCurrent,
                totalInvested = totalInvested,
                totalProfit = totalProfit,
                totalProfitPercent = totalProfitPercent
            )
        }

        item {
            Text(
                text = "Meus ativos",
                color = WhiteText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (investments.isEmpty()) {
            item {
                EmptyPortfolioCard()
            }
        } else {
            items(
                items = investments,
                key = { investment -> investment.id }
            ) { investment ->
                InvestmentCard(investment = investment)
            }
        }
    }
}

@Composable
private fun PortfolioSummaryCard(
    totalCurrent: BigDecimal,
    totalInvested: BigDecimal,
    totalProfit: BigDecimal,
    totalProfitPercent: BigDecimal
) {
    val isPositive = totalProfit >= BigDecimal.ZERO
    val profitColor = if (isPositive) Green else Red

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(DeepPurple, Purple)
                ),
                shape = RoundedCornerShape(22.dp)
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Saldo em investimentos",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = totalCurrent.toCurrency(),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Valor investido",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )

                    Text(
                        text = totalInvested.toCurrency(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Rendimento",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isPositive) {
                                Icons.Default.ArrowUpward
                            } else {
                                Icons.Default.ArrowDownward
                            },
                            contentDescription = null,
                            tint = profitColor,
                            modifier = Modifier.size(14.dp)
                        )

                        Text(
                            text = totalProfit.abs().toCurrency() +
                                " (" + totalProfitPercent.abs().toPercent() + ")",
                            color = profitColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InvestmentCard(
    investment: Investment
) {
    val profit = investment.currentValue.subtract(
        investment.investedAmount
    )

    val isPositive = profit >= BigDecimal.ZERO
    val profitColor = if (isPositive) Green else Red
    val typeColor = investmentTypeColor(investment.type)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = typeColor.copy(alpha = 0.16f),
                            shape = RoundedCornerShape(13.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = investmentTypeIcon(investment.type),
                        contentDescription = null,
                        tint = typeColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = investment.name,
                        color = WhiteText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = investmentTypeLabel(investment.type),
                        color = SecondaryText,
                        fontSize = 13.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = investment.currentValue.toCurrency(),
                        color = WhiteText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = (if (isPositive) "+" else "-") +
                            investment.profitability.abs().toPercent(),
                        color = profitColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Investido: " +
                        investment.investedAmount.toCurrency(),
                    color = SecondaryText,
                    fontSize = 12.sp
                )

                Text(
                    text = (if (isPositive) "+" else "") +
                        profit.toCurrency(),
                    color = profitColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun EmptyPortfolioCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ShowChart,
                contentDescription = null,
                tint = PurpleLight,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Você ainda não possui investimentos",
                color = WhiteText,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Comece a investir para ver sua carteira aqui.",
                color = SecondaryText,
                fontSize = 13.sp
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
        Text(
            text = message,
            color = Red,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Tentar novamente",
                tint = PurpleLight,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

private fun List<Investment>.sumOf(
    selector: (Investment) -> BigDecimal
): BigDecimal {
    return fold(BigDecimal.ZERO) { acc, investment ->
        acc.add(selector(investment))
    }
}

private fun BigDecimal.toCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}

private fun BigDecimal.toPercent(): String {
    val formatted = setScale(2, RoundingMode.HALF_UP)
        .toPlainString()
        .replace(".", ",")

    return "$formatted%"
}

private fun investmentTypeLabel(
    type: String
): String {
    return when (type.uppercase()) {
        "RENDA_FIXA",
        "FIXED_INCOME" ->
            "Renda Fixa"

        "FUNDOS",
        "FUNDS" ->
            "Fundos"

        "ACOES",
        "STOCKS" ->
            "Ações"

        "TESOURO_DIRETO",
        "TREASURY" ->
            "Tesouro Direto"

        "CRIPTO",
        "CRYPTO" ->
            "Criptomoedas"

        else ->
            type.replace("_", " ")
                .lowercase()
                .replaceFirstChar { it.uppercase() }
    }
}

private fun investmentTypeColor(
    type: String
): Color {
    return when (type.uppercase()) {
        "RENDA_FIXA", "FIXED_INCOME" -> Blue
        "FUNDOS", "FUNDS" -> Purple
        "ACOES", "STOCKS" -> Green
        "TESOURO_DIRETO", "TREASURY" -> Orange
        "CRIPTO", "CRYPTO" -> Orange
        else -> PurpleLight
    }
}

private fun investmentTypeIcon(
    type: String
): ImageVector {
    return when (type.uppercase()) {
        "RENDA_FIXA", "FIXED_INCOME" -> Icons.Default.Savings
        "FUNDOS", "FUNDS" -> Icons.Default.PieChart
        "ACOES", "STOCKS" -> Icons.Default.ShowChart
        "TESOURO_DIRETO", "TREASURY" -> Icons.Default.AccountBalance
        "CRIPTO", "CRYPTO" -> Icons.Default.CurrencyBitcoin
        else -> Icons.Default.ShowChart
    }
}
