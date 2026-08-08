package com.jucelio.jbankmobile.ui.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val JBankDarkBlue = Color(0xFF001D45)
private val JBankNavy = Color(0xFF002B5F)
private val JBankCyan = Color(0xFF10C8C8)
private val JBankLightCyan = Color(0xFF28D7CB)

@Composable
fun WelcomeRoute(
    onNavigateToAccountIdentification: () -> Unit,
    onNavigateToOpenAccount: () -> Unit,
    viewModel: WelcomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                WelcomeNavigationEvent.AccountIdentification -> {
                    onNavigateToAccountIdentification()
                }

                WelcomeNavigationEvent.OpenAccount -> {
                    onNavigateToOpenAccount()
                }
            }
        }
    }

    WelcomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun WelcomeScreen(
    uiState: WelcomeUiState,
    onEvent: (WelcomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        JBankDarkBlue,
                        JBankNavy,
                        JBankDarkBlue
                    )
                )
            )
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 32.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.height(72.dp)
            )

            WelcomeLogo()

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Seu banco. Seu futuro.",
                color = Color.White.copy(
                    alpha = 0.92f
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            FinancialWaveDecoration()

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    onEvent(
                        WelcomeEvent.AccessAccountClicked
                    )
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = JBankCyan,
                    contentColor = Color.White
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Acessar minha conta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedButton(
                onClick = {
                    onEvent(
                        WelcomeEvent.OpenAccountClicked
                    )
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.White.copy(
                        alpha = 0.75f
                    )
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Abrir uma conta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            uiState.errorMessage?.let { error ->
                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            SecurityBenefits()
        }
    }
}

@Composable
private fun WelcomeLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            JBankLightCyan,
                            Color(0xFF009BC7)
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 8.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "J",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.size(14.dp)
        )

        Text(
            text = "JBank",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun FinancialWaveDecoration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "﹏﹏﹏﹏﹏﹏﹏﹏﹏",
            color = JBankCyan.copy(
                alpha = 0.85f
            ),
            fontSize = 42.sp,
            maxLines = 1
        )
    }
}

@Composable
private fun SecurityBenefits() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {
        SecurityBenefit(
            icon = Icons.Outlined.Security,
            title = "Segurança",
            subtitle = "em primeiro lugar",
            modifier = Modifier.weight(1f)
        )

        SecurityBenefit(
            icon = Icons.Outlined.Lock,
            title = "Seus dados",
            subtitle = "protegidos",
            modifier = Modifier.weight(1f)
        )

        SecurityBenefit(
            icon = Icons.Outlined.VerifiedUser,
            title = "Tecnologia",
            subtitle = "que simplifica",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SecurityBenefit(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            horizontal = 4.dp
        ),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = JBankCyan,
            modifier = Modifier.size(24.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = subtitle,
            color = Color.White.copy(
                alpha = 0.72f
            ),
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            uiState = WelcomeUiState(),
            onEvent = {}
        )
    }
}