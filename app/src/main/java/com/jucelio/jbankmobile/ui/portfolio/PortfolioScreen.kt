package com.jucelio.jbankmobile.ui.portfolio

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jucelio.jbankmobile.domain.model.Investment
import com.jucelio.jbankmobile.domain.model.InvestmentCategory
import com.jucelio.jbankmobile.domain.model.Portfolio
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

private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFA8A5B7)
private val DividerColor = Color(0xFF292B3D)

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
                        text = "Meu Portfólio",
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

            state.data == null -> {
                ErrorContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = state.errorMessage
                        ?: "Não foi possível carregar o seu portfólio.",
                    onRefresh = onRefresh
                )
            }

            else -> {
                PortfolioContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    portfolio = state.data
                )
            }
        }
    }
}

@Composable
private fun PortfolioContent(
    modifier: Modifier,
    portfolio: Portfolio
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            TotalBalanceCard(
                portfolio = portfolio
            )
        }

        item {
            SectionTitle(
                title = "Diversificação da carteira"
            )
        }

        item {
            AllocationCard(
                portfolio = portfolio
            )
        }

        item {
            SectionTitle(
                title = "Meus ativos"
            )
        }

        if (portfolio.investments.isEmpty()) {
            item {
                EmptyInvestments()
            }
        } else {
            items(
                items = portfolio.investments,
                key = { investment ->
                    investment.id
                }
            ) { investment ->
                InvestmentCard(
                    investment = investment
                )
            }
        }
    }
}

@Composable
private fun TotalBalanceCard(
    portfolio: Portfolio
) {
    val isPositive = portfolio.profit.signum() >= 0

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
                Text(
                    text = "Saldo investido",
                    color = Color.White.copy(alpha = 0.88f),
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = portfolio.currentBalance.toCurrency(),
                    color = Color.White,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isPositive) {
                            Icons.Default.TrendingUp
                        } else {
                            Icons.Default.TrendingDown
                        },
                        contentDescription = null,
                        tint = if (isPositive) Green else Red,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "${portfolio.profit.toCurrency()} " +
                            "(${portfolio.profitPercentage.toPercentage()})",
                        color = if (isPositive) Green else Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = "Total aplicado: " +
                        portfolio.totalInvested.toCurrency(),
                    color = Color.White.copy(alpha = 0.82f),
                    fontSize = 13.sp
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
private fun AllocationCard(
    portfolio: Portfolio
) {
    val allocations = portfolio.allocationByCategory()

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
            if (allocations.isEmpty()) {
                Text(
                    text = "Você ainda não possui ativos na carteira.",
                    color = SecondaryText,
                    fontSize = 13.sp
                )
            } else {
                allocations.forEachIndexed {
                        index,
                        allocation ->

                    AllocationRow(
                        allocation = allocation
                    )

                    if (index < allocations.lastIndex) {
                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AllocationRow(
    allocation: CategoryAllocation
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = allocation.category.icon(),
                contentDescription = null,
                tint = PurpleLight,
                modifier = Modifier.size(18.dp)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = allocation.category.label(),
                modifier = Modifier.weight(1f),
                color = WhiteText,
                fontSize = 13.sp
            )

            Text(
                text = allocation.percentage.toPercentage(),
                color = SecondaryText,
                fontSize = 12.sp
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(DividerColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        allocation.fraction()
                    )
                    .fillMaxSize()
                    .background(PurpleLight)
            )
        }
    }
}

@Composable
private fun InvestmentCard(
    investment: Investment
) {
    val isPositive = investment.profit.signum() >= 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        Purple.copy(alpha = 0.18f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = investment.category.icon(),
                    contentDescription = null,
                    tint = PurpleLight,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = investment.name,
                    color = WhiteText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = investment.category.label(),
                    color = SecondaryText,
                    fontSize = 11.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = investment.currentAmount.toCurrency(),
                    color = WhiteText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = investment.profitPercentage.toPercentage(
                        withSign = true
                    ),
                    color = if (isPositive) Green else Red,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun EmptyInvestments() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.PieChart,
            contentDescription = null,
            tint = PurpleLight,
            modifier = Modifier.size(38.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Você ainda não possui investimentos",
            color = SecondaryText,
            fontSize = 13.sp
        )
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

private data class CategoryAllocation(
    val category: InvestmentCategory,
    val amount: BigDecimal,
    val percentage: BigDecimal
) {
    fun fraction(): Float {
        return (percentage.toFloat() / 100f).coerceIn(0f, 1f)
    }
}

private fun Portfolio.allocationByCategory(): List<CategoryAllocation> {
    if (currentBalance.signum() == 0) {
        return emptyList()
    }

    return investments
        .groupBy { investment -> investment.category }
        .map { (category, categoryInvestments) ->
            val amount = categoryInvestments.fold(BigDecimal.ZERO) { total, investment ->
                total.add(investment.currentAmount)
            }

            val percentage = amount
                .divide(currentBalance, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal(100))

            CategoryAllocation(
                category = category,
                amount = amount,
                percentage = percentage
            )
        }
        .sortedByDescending { allocation ->
            allocation.amount
        }
}

private fun InvestmentCategory.label(): String {
    return when (this) {
        InvestmentCategory.RENDA_FIXA -> "Renda Fixa"
        InvestmentCategory.TESOURO_DIRETO -> "Tesouro Direto"
        InvestmentCategory.FUNDOS -> "Fundos"
        InvestmentCategory.ACOES -> "Ações"
        InvestmentCategory.CRIPTO -> "Criptomoedas"
    }
}

private fun InvestmentCategory.icon(): ImageVector {
    return when (this) {
        InvestmentCategory.RENDA_FIXA -> Icons.Default.Savings
        InvestmentCategory.TESOURO_DIRETO -> Icons.Default.AccountBalance
        InvestmentCategory.FUNDOS -> Icons.Default.Diversity3
        InvestmentCategory.ACOES -> Icons.Default.ShowChart
        InvestmentCategory.CRIPTO -> Icons.Default.CurrencyBitcoin
    }
}

private fun BigDecimal.toCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}

private fun BigDecimal.toPercentage(
    withSign: Boolean = false
): String {
    val rounded = this.setScale(2, RoundingMode.HALF_UP)

    val prefix = if (withSign && rounded.signum() > 0) {
        "+"
    } else {
        ""
    }

    return "$prefix$rounded%"
}
