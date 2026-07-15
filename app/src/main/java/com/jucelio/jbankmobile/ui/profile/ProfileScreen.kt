package com.jucelio.jbankmobile.ui.profile

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
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundColor = Color(0xFF050611)
private val SurfaceColor = Color(0xFF101222)
private val SurfaceLightColor = Color(0xFF17192C)
private val PrimaryPurple = Color(0xFF8B3DFF)
private val SecondaryPurple = Color(0xFFB06CFF)
private val TextPrimary = Color(0xFFF7F3FF)
private val TextSecondary = Color(0xFFAAA7B8)
private val DividerColor = Color(0xFF292B3E)
private val DangerColor = Color(0xFFFF5268)

@Immutable
data class ProfileUiState(
    val agency: String = "0001",
    val account: String = "12345-6",
    val accountType: String = "Conta Corrente",
    val customerSince: String = "05/2023",
    val customerLevel: String = "Nível Infinity",
    val totalAssets: String = "R$ 48.750,30",
    val activeGoals: String = "3",
    val cashback: String = "R$ 156,80",
    val points: String = "2.450 pts",
    val lastAccess: String = "14/07/2026 às 19:42",
    val appVersion: String = "1.0.0"
)

@Composable
fun ProfileScreen(
    userName: String = "Jucelio Farias Coelho",
    userEmail: String = "admin@jbank.com",
    uiState: ProfileUiState = ProfileUiState(),

    onHomeClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onTransactionsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onLogout: () -> Unit,

    onMyProfileClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onCardsClick: () -> Unit = {},
    onBeneficiariesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,

        bottomBar = {
            ProfileBottomNavigation(
                onHomeClick = onHomeClick,
                onAccountsClick = onAccountsClick,
                onTransactionsClick = onTransactionsClick,
                onNotificationsClick = onNotificationsClick
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                ProfileHeader(
                    onNotifications = onNotificationsClick,
                    onSettings = onSettingsClick
                )
            }

            item {
                Text(
                    text = "Meu Perfil",
                    color = TextPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Gerencie suas informações e preferências.",
                    color = TextSecondary,
                    fontSize = 16.sp
                )
            }

            item {
                UserProfileCard(
                    userName = userName,
                    userEmail = userEmail,
                    uiState = uiState,
                    onEditProfile = onMyProfileClick
                )
            }

            item {
                ProfileSummaryCard(
                    uiState = uiState
                )
            }

            item {
                Text(
                    text = "Gerenciar",
                    color = TextPrimary,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                ManagementCard(
                    items = listOf(
                        ProfileMenuItem(
                            title = "Dados pessoais",
                            subtitle = "Atualize suas informações cadastrais",
                            icon = Icons.Default.Person,
                            onClick = onMyProfileClick
                        ),

                        ProfileMenuItem(
                            title = "Segurança",
                            subtitle = "Gerencie senha, biometria e dispositivos",
                            icon = Icons.Default.Security,
                            onClick = onSecurityClick
                        ),

                        ProfileMenuItem(
                            title = "Notificações",
                            subtitle = "Escolha como receber alertas",
                            icon = Icons.Default.Notifications,
                            onClick = onNotificationsClick
                        ),

                        ProfileMenuItem(
                            title = "Cartões",
                            subtitle = "Gerencie seus cartões e limites",
                            icon = Icons.Default.CreditCard,
                            onClick = onCardsClick
                        ),

                        ProfileMenuItem(
                            title = "Beneficiários",
                            subtitle = "Gerencie seus contatos favoritos",
                            icon = Icons.Default.AccountCircle,
                            onClick = onBeneficiariesClick
                        ),

                        ProfileMenuItem(
                            title = "Benefícios",
                            subtitle = "Cashback, pontos e vantagens",
                            icon = Icons.Default.CardGiftcard,
                            onClick = onBeneficiariesClick
                        ),

                        ProfileMenuItem(
                            title = "Configurações",
                            subtitle = "Preferências e aparência do aplicativo",
                            icon = Icons.Default.Settings,
                            onClick = onSettingsClick
                        ),

                        ProfileMenuItem(
                            title = "Ajuda e suporte",
                            subtitle = "Fale com a equipe ou consulte dúvidas",
                            icon = Icons.Default.HelpOutline,
                            onClick = onHelpClick
                        ),

                        ProfileMenuItem(
                            title = "Sobre o JBank",
                            subtitle = "Versão, tecnologias e informações",
                            icon = Icons.Default.Info,
                            onClick = onAboutClick
                        )
                    )
                )
            }

            item {
                LogoutCard(
                    onClick = {
                        showLogoutDialog = true
                    }
                )
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Último acesso: ${uiState.lastAccess}",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Versão ${uiState.appVersion}",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }

    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                onLogout()
            },

            onDismiss = {
                showLogoutDialog = false
            }
        )
    }
}

@Composable
private fun ProfileHeader(
    onNotifications: () -> Unit,
    onSettings: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SecondaryPurple,
                            PrimaryPurple,
                            Color(0xFF4D0FAD)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "J",
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Black
            )
        }

        Text(
            text = "JBank",
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            color = TextPrimary,
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold
        )

        BadgedBox(
            badge = {
                Badge(
                    containerColor = PrimaryPurple
                )
            }
        ) {
            IconButton(
                onClick = onNotifications
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = TextPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        IconButton(
            onClick = onSettings
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configurações",
                tint = TextPrimary,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun UserProfileCard(
    userName: String,
    userEmail: String,
    uiState: ProfileUiState,
    onEditProfile: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onEditProfile
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileAvatar(
                    userName = userName,
                    onEditProfile = onEditProfile
                )

                Column(
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userName,
                            color = TextPrimary,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Cliente verificado",
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .size(19.dp),
                            tint = PrimaryPurple
                        )
                    }

                    Text(
                        text = userEmail,
                        modifier = Modifier.padding(
                            top = 4.dp
                        ),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Cliente JBank",
                        modifier = Modifier.padding(
                            top = 3.dp
                        ),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )

                    Surface(
                        modifier = Modifier.padding(
                            top = 10.dp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        color = PrimaryPurple.copy(
                            alpha = 0.13f
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = PrimaryPurple
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 7.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(17.dp),
                                tint = SecondaryPurple
                            )

                            Text(
                                text = uiState.customerLevel,
                                modifier = Modifier.padding(
                                    start = 6.dp
                                ),
                                color = SecondaryPurple,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Abrir perfil",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(
                    vertical = 22.dp
                ),
                thickness = 1.dp,
                color = DividerColor
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AccountInformation(
                    title = "Agência",
                    value = uiState.agency,
                    icon = Icons.Default.Person,
                    modifier = Modifier.weight(1f)
                )

                AccountInformation(
                    title = "Conta",
                    value = uiState.account,
                    icon = Icons.Default.AccountBalanceWallet,
                    modifier = Modifier.weight(1f)
                )

                AccountInformation(
                    title = "Tipo de conta",
                    value = uiState.accountType,
                    icon = Icons.Default.CreditCard,
                    modifier = Modifier.weight(1.25f)
                )

                AccountInformation(
                    title = "Cliente desde",
                    value = uiState.customerSince,
                    icon = Icons.Default.Star,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProfileAvatar(
    userName: String,
    onEditProfile: () -> Unit
) {
    Box {
        Box(
            modifier = Modifier
                .size(92.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            PrimaryPurple,
                            Color(0xFF372050)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getInitials(userName),
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(
            onClick = onEditProfile,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(32.dp)
                .clip(CircleShape)
                .background(PrimaryPurple)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar perfil",
                modifier = Modifier.size(16.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun AccountInformation(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(21.dp),
            tint = PrimaryPurple
        )

        Text(
            text = title,
            modifier = Modifier.padding(
                top = 7.dp
            ),
            color = TextSecondary,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = value,
            modifier = Modifier.padding(
                top = 5.dp
            ),
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProfileSummaryCard(
    uiState: ProfileUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Resumo do seu perfil",
                    modifier = Modifier.weight(1f),
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                TextButton(
                    onClick = {}
                ) {
                    Text(
                        text = "Ver detalhes",
                        color = SecondaryPurple
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = SecondaryPurple,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SummaryInformation(
                    title = "Patrimônio total",
                    value = uiState.totalAssets,
                    icon = Icons.Default.BarChart,
                    modifier = Modifier.weight(1f)
                )

                SummaryInformation(
                    title = "Objetivos ativos",
                    value = uiState.activeGoals,
                    icon = Icons.Default.ShowChart,
                    modifier = Modifier.weight(1f)
                )

                SummaryInformation(
                    title = "Cashback",
                    value = uiState.cashback,
                    icon = Icons.Default.CardGiftcard,
                    modifier = Modifier.weight(1f)
                )

                SummaryInformation(
                    title = "JPoints",
                    value = uiState.points,
                    icon = Icons.Default.Star,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SummaryInformation(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(
                    PrimaryPurple.copy(
                        alpha = 0.15f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryPurple
            )
        }

        Text(
            text = title,
            modifier = Modifier.padding(
                top = 10.dp
            ),
            color = TextSecondary,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = value,
            modifier = Modifier.padding(
                top = 5.dp
            ),
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

private data class ProfileMenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
private fun ManagementCard(
    items: List<ProfileMenuItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Column {
            items.forEachIndexed { index, item ->

                ProfileMenuRow(
                    item = item
                )

                if (index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 72.dp
                        ),
                        thickness = 1.dp,
                        color = DividerColor
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileMenuRow(
    item: ProfileMenuItem
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = item.onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(
                        RoundedCornerShape(13.dp)
                    )
                    .background(
                        PrimaryPurple.copy(
                            alpha = 0.14f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = PrimaryPurple
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = item.subtitle,
                    modifier = Modifier.padding(
                        top = 3.dp
                    ),
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Abrir ${item.title}",
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun LogoutCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DividerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 21.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = null,
                tint = DangerColor
            )

            Text(
                text = "Sair da conta",
                modifier = Modifier.padding(
                    start = 10.dp
                ),
                color = DangerColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        containerColor = SurfaceColor,

        icon = {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = null,
                tint = DangerColor
            )
        },

        title = {
            Text(
                text = "Sair do JBank?",
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        },

        text = {
            Text(
                text = "Você precisará informar suas credenciais novamente para acessar sua conta.",
                color = TextSecondary
            )
        },

        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DangerColor
                )
            ) {
                Text("Sair")
            }
        },

        dismissButton = {
            OutlinedButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancelar",
                    color = TextPrimary
                )
            }
        }
    )
}

@Composable
private fun ProfileBottomNavigation(
    onHomeClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onTransactionsClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = SurfaceLightColor
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
            colors = profileNavigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onAccountsClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = "Contas"
                )
            },
            label = {
                Text("Contas")
            },
            colors = profileNavigationColors()
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
            colors = profileNavigationColors()
        )

        NavigationBarItem(
            selected = false,
            onClick = onNotificationsClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações"
                )
            },
            label = {
                Text("Notificações")
            },
            colors = profileNavigationColors()
        )

        NavigationBarItem(
            selected = true,
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
            colors = profileNavigationColors()
        )
    }
}

@Composable
private fun profileNavigationColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = PrimaryPurple,
        selectedTextColor = SecondaryPurple,
        indicatorColor = PrimaryPurple.copy(
            alpha = 0.15f
        ),
        unselectedIconColor = TextSecondary,
        unselectedTextColor = TextSecondary
    )

private fun getInitials(
    name: String
): String {
    val words = name
        .trim()
        .split(" ")
        .filter {
            it.isNotBlank()
        }

    return when {
        words.isEmpty() -> "JB"

        words.size == 1 ->
            words.first()
                .take(2)
                .uppercase()

        else ->
            "${words.first().first()}${words.last().first()}"
                .uppercase()
    }
}