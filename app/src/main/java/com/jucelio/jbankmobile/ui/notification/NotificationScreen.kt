package com.jucelio.jbankmobile.ui.notification

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
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
import com.jucelio.jbankmobile.domain.model.Notification
private val Navy = Color(0xFF031B3A)
private val Green = Color(0xFF00C96B)
private val Background = Color(0xFFF4F6FA)
private val MutedText = Color(0xFF667085)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    state: NotificationUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onHomeClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onTransactionsClick: () -> Unit
) {
    Scaffold(
        containerColor = Background,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notificações",
                        color = Color.White,
                        fontSize = 22.sp,
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
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Atualizar",
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Navy
                )
            )
        },

        bottomBar = {
            NotificationBottomBar(
                onHomeClick = onHomeClick,
                onAccountsClick = onAccountsClick,
                onTransactionsClick = onTransactionsClick
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
                    CircularProgressIndicator(
                        color = Green
                    )
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
                NotificationContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    notifications = state.notifications
                )
            }
        }
    }
}

@Composable
private fun NotificationContent(
    modifier: Modifier,
    notifications: List<Notification>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 18.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "Suas notificações",
                color = Navy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        if (notifications.isEmpty()) {
            item {
                EmptyNotificationCard()
            }
        } else {
            items(
                items = notifications,
                key = { notification ->
                    notification.id
                }
            ) { notification ->
                NotificationCard(
                    notification = notification
                )
            }
        }
    }
}

@Composable
private fun NotificationCard(
    notification: Notification
) {
    val color = notificationColor(notification.type)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.read) {
                Color.White
            } else {
                color.copy(alpha = 0.07f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = color.copy(alpha = 0.14f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notificationIcon(
                        notification.type
                    ),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(13.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {
                    Text(
                        text = notification.title,
                        color = Navy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (!notification.read) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .background(
                                    color = Green,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = notification.message,
                    color = MutedText,
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )

                if (notification.createdAt.isNotBlank()) {
                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = formatNotificationDate(
                            notification.createdAt
                        ),
                        color = MutedText,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyNotificationCard() {
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
                .padding(30.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(52.dp)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Nenhuma notificação",
                color = Navy,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Novidades e movimentações aparecerão aqui.",
                color = MutedText,
                fontSize = 14.sp
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
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = Navy,
            modifier = Modifier.size(54.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Text(
            text = message,
            color = Navy,
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
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

@Composable
private fun NotificationBottomBar(
    onHomeClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onTransactionsClick: () -> Unit
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
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = "Contas"
                )
            },
            label = {
                Text("Contas")
            },
            colors = navigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onTransactionsClick,
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
            selected = true,
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

private fun notificationColor(
    type: String
): Color {
    return when (type.uppercase()) {
        "SUCCESS" -> Green
        "WARNING" -> Color(0xFFF59E0B)
        "ERROR" -> Color(0xFFEF4444)
        else -> Color(0xFF2563EB)
    }
}

private fun notificationIcon(
    type: String
): ImageVector {
    return when (type.uppercase()) {
        "SUCCESS" -> Icons.Default.CheckCircle
        "WARNING" -> Icons.Default.Warning
        "ERROR" -> Icons.Default.Error
        else -> Icons.Default.Info
    }
}

private fun formatNotificationDate(
    createdAt: String
): String {
    return createdAt
        .replace("T", " ")
        .take(16)
}