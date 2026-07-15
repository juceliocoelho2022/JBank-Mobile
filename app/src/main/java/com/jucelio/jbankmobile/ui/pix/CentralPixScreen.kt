package com.jucelio.jbankmobile.ui.pix

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.Immutable
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

private val PixBackground = Color(0xFF060712)
private val PixSurface = Color(0xFF111322)
private val PixSurfaceLight = Color(0xFF181A2E)
private val PixPurple = Color(0xFF8B2CFF)
private val PixPurpleLight = Color(0xFFB45CFF)
private val PixDeepPurple = Color(0xFF4C0DB8)
private val PixGreen = Color(0xFF35E36F)
private val PixBlue = Color(0xFF5487FF)
private val PixOrange = Color(0xFFFFB020)
private val PixWhite = Color(0xFFF8F5FF)
private val PixSecondary = Color(0xFFA8A5B7)
private val PixDivider = Color(0xFF292B3D)

@Immutable
private data class PixKeyItem(
    val icon: ImageVector,
    val value: String,
    val type: String,
    val color: Color
)

@Immutable
private data class PixTransactionItem(
    val title: String,
    val subtitle: String,
    val value: String,
    val date: String,
    val incoming: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CentralPixScreen(
    onBack: () -> Unit,
    onSendPixClick: () -> Unit,
    onReadQrCodeClick: () -> Unit = {},
    onCopyPasteClick: () -> Unit = {},
    onContactPixClick: () -> Unit = {},
    onGiftPixClick: () -> Unit = {},
    onCreateQrCodeClick: () -> Unit = {},
    onPixKeysClick: () -> Unit = {},
    onStatementClick: () -> Unit = {},
    onChargesClick: () -> Unit = {},
    onScheduledPixClick: () -> Unit = {},
    onReportFraudClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onCardsClick: () -> Unit = {},
    onInvestmentsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val pixKeys = listOf(
        PixKeyItem(
            icon = Icons.Default.Email,
            value = "jucelio.farias@jbank.com.br",
            type = "E-mail",
            color = PixGreen
        ),
        PixKeyItem(
            icon = Icons.Default.Phone,
            value = "(11) 99999-8888",
            type = "Telefone",
            color = PixBlue
        ),
        PixKeyItem(
            icon = Icons.Default.Key,
            value = "7f3a6b8e-1d2c-4e5f-b123-9c8d7e6f5a4b",
            type = "Aleatória",
            color = PixOrange
        )
    )

    val transactions = listOf(
        PixTransactionItem(
            title = "PIX enviado",
            subtitle = "Maria Silva",
            value = "- R$ 150,00",
            date = "Hoje, 10:45",
            incoming = false
        ),
        PixTransactionItem(
            title = "PIX recebido",
            subtitle = "João Santos",
            value = "+ R$ 250,00",
            date = "Ontem, 18:30",
            incoming = true
        ),
        PixTransactionItem(
            title = "PIX enviado",
            subtitle = "Mercado Livre",
            value = "- R$ 89,90",
            date = "Ontem, 14:22",
            incoming = false
        ),
        PixTransactionItem(
            title = "PIX recebido",
            subtitle = "Empresa LTDA",
            value = "+ R$ 1.200,00",
            date = "03/07, 09:15",
            incoming = true
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PixBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PIX",
                        color = PixWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = PixWhite
                        )
                    }
                },

                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Ajuda",
                            tint = PixWhite
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Mais opções",
                            tint = PixWhite
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PixBackground
                )
            )
        },

        bottomBar = {
            PixBottomNavigation(
                onHomeClick = onHomeClick,
                onCardsClick = onCardsClick,
                onInvestmentsClick = onInvestmentsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 10.dp,
                bottom = 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PixHeaderCard()
            }

            item {
                TransferAndPayCard(
                    onSendPixClick = onSendPixClick,
                    onReadQrCodeClick = onReadQrCodeClick,
                    onCopyPasteClick = onCopyPasteClick,
                    onContactPixClick = onContactPixClick
                )
            }

            item {
                ReceiveCard(
                    onCreateQrCodeClick = onCreateQrCodeClick,
                    onChargesClick = onChargesClick
                )
            }

            item {
                PixKeysCard(
                    keys = pixKeys,
                    onSeeAllClick = onPixKeysClick
                )
            }

            item {
                LatestPixTransactionsCard(
                    transactions = transactions,
                    onSeeAllClick = onStatementClick
                )
            }

            item {
                PixSecurityBanner(
                    onClick = onReportFraudClick
                )
            }

            item {
                PixAdvantages()
            }
        }
    }
}

@Composable
private fun PixHeaderCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
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
                            Color(0xFF210648),
                            PixDeepPurple,
                            Color(0xFF371070)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Envie, receba e\npague com PIX.",
                        color = Color.White,
                        fontSize = 26.sp,
                        lineHeight = 31.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "É rápido, seguro e funciona 24h todos os dias.",
                        color = Color.White.copy(alpha = 0.80f),
                        fontSize = 14.sp,
                        lineHeight = 19.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(108.dp)
                        .clip(
                            RoundedCornerShape(28.dp)
                        )
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    PixPurple.copy(alpha = 0.70f),
                                    PixPurpleLight.copy(alpha = 0.25f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(67.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TransferAndPayCard(
    onSendPixClick: () -> Unit,
    onReadQrCodeClick: () -> Unit,
    onCopyPasteClick: () -> Unit,
    onContactPixClick: () -> Unit
) {
    PremiumPixCard {
        Text(
            text = "Transferir ou pagar",
            color = PixWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PixMainAction(
                icon = Icons.Default.Key,
                title = "Chave PIX",
                onClick = onSendPixClick
            )

            PixMainAction(
                icon = Icons.Default.QrCodeScanner,
                title = "QR Code",
                onClick = onReadQrCodeClick
            )

            PixMainAction(
                icon = Icons.Default.ContentCopy,
                title = "Copia e Cola",
                onClick = onCopyPasteClick
            )

            PixMainAction(
                icon = Icons.Default.Person,
                title = "Contato",
                onClick = onContactPixClick
            )
        }
    }
}

@Composable
private fun RowScope.PixMainAction(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            PixPurple,
                            PixDeepPurple
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Text(
            text = title,
            color = PixWhite,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
private fun ReceiveCard(
    onCreateQrCodeClick: () -> Unit,
    onChargesClick: () -> Unit
) {
    PremiumPixCard {
        Text(
            text = "Receber",
            color = PixWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        PixOptionRow(
            icon = Icons.Default.QrCode,
            title = "Gerar QR Code",
            subtitle = "Gere seu QR Code para receber pagamentos",
            onClick = onCreateQrCodeClick
        )

        HorizontalDivider(
            modifier = Modifier.padding(start = 56.dp),
            color = PixDivider
        )

        PixOptionRow(
            icon = Icons.Default.RequestQuote,
            title = "Cobrança PIX",
            subtitle = "Crie cobranças com vencimento",
            onClick = onChargesClick
        )
    }
}

@Composable
private fun PixOptionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(CircleShape)
                .background(
                    PixPurple.copy(alpha = 0.24f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = PixPurpleLight,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = PixWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = subtitle,
                color = PixSecondary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Abrir",
            tint = PixSecondary,
            modifier = Modifier.size(17.dp)
        )
    }
}

@Composable
private fun PremiumPixCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(
            containerColor = PixSurface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = PixDivider
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
private fun PixKeysCard(
    keys: List<PixKeyItem>,
    onSeeAllClick: () -> Unit
) {
    PremiumPixCard {
        CardTitle(
            title = "Minhas chaves",
            action = "Ver todas",
            onActionClick = onSeeAllClick
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        keys.forEachIndexed { index, key ->
            PixKeyRow(
                item = key
            )

            if (index < keys.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        start = 52.dp
                    ),
                    color = PixDivider
                )
            }
        }
    }
}

@Composable
private fun PixKeyRow(
    item: PixKeyItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    item.color.copy(alpha = 0.22f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.type,
                tint = item.color,
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = item.value,
            modifier = Modifier.weight(1f),
            color = PixWhite,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(
                    PixPurple.copy(alpha = 0.13f)
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 6.dp
                )
        ) {
            Text(
                text = item.type,
                color = PixPurpleLight,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun LatestPixTransactionsCard(
    transactions: List<PixTransactionItem>,
    onSeeAllClick: () -> Unit
) {
    PremiumPixCard {
        CardTitle(
            title = "Últimas transações PIX",
            action = "Ver todas",
            onActionClick = onSeeAllClick
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        transactions.forEachIndexed { index, transaction ->
            PixTransactionRow(
                transaction = transaction
            )

            if (index < transactions.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        start = 52.dp
                    ),
                    color = PixDivider
                )
            }
        }
    }
}

@Composable
private fun PixTransactionRow(
    transaction: PixTransactionItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    if (transaction.incoming) {
                        PixGreen.copy(alpha = 0.20f)
                    } else {
                        PixBlue.copy(alpha = 0.20f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (transaction.incoming) {
                    Icons.Default.ArrowDownward
                } else {
                    Icons.Default.ArrowUpward
                },
                contentDescription = null,
                tint = if (transaction.incoming) {
                    PixGreen
                } else {
                    PixBlue
                },
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.title,
                color = PixWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = transaction.subtitle,
                color = PixSecondary,
                fontSize = 11.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = transaction.value,
                color = if (transaction.incoming) {
                    PixGreen
                } else {
                    PixWhite
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = transaction.date,
                color = PixSecondary,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun PixSecurityBanner(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            PixDeepPurple,
                            PixPurple,
                            Color(0xFF35106C)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "PIX no JBank é segurança e praticidade",
                        color = Color.White,
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = "Confira sempre os dados antes de confirmar uma transferência.",
                        color = Color.White.copy(alpha = 0.78f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                        .background(
                            Color.White.copy(alpha = 0.10f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(51.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PixAdvantages() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        PixAdvantage(
            icon = Icons.Default.Security,
            title = "Segurança",
            subtitle = "Dados protegidos"
        )

        PixAdvantage(
            icon = Icons.Default.AccountBalanceWallet,
            title = "Disponível 24h",
            subtitle = "A qualquer hora"
        )

        PixAdvantage(
            icon = Icons.Default.SwapHoriz,
            title = "Rápido",
            subtitle = "Em segundos"
        )
    }
}

@Composable
private fun RowScope.PixAdvantage(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(CircleShape)
                .background(
                    PixPurple.copy(alpha = 0.22f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = PixPurpleLight,
                modifier = Modifier.size(23.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = title,
            color = PixWhite,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = subtitle,
            color = PixSecondary,
            fontSize = 8.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CardTitle(
    title: String,
    action: String,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = PixWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = action,
            color = PixPurpleLight,
            fontSize = 11.sp,
            modifier = Modifier.clickable(
                onClick = onActionClick
            )
        )
    }
}

@Composable
private fun PixBottomNavigation(
    onHomeClick: () -> Unit,
    onCardsClick: () -> Unit,
    onInvestmentsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = PixSurfaceLight
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
            colors = pixNavigationColors()
        )

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "PIX"
                )
            },
            label = {
                Text("PIX")
            },
            colors = pixNavigationColors()
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
            colors = pixNavigationColors()
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
            colors = pixNavigationColors()
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
            colors = pixNavigationColors()
        )
    }
}

@Composable
private fun pixNavigationColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = PixPurpleLight,
        selectedTextColor = PixPurpleLight,
        indicatorColor = PixPurple.copy(alpha = 0.18f),
        unselectedIconColor = PixSecondary,
        unselectedTextColor = PixSecondary
    )