package com.jucelio.jbankmobile.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
private val BackgroundTop = Color(0xFF050611)
private val BackgroundBottom = Color(0xFF08031A)
private val SurfaceDark = Color(0xFF101220)
private val SurfaceField = Color(0xFF0D0F1C)
private val Purple = Color(0xFF8B2CFF)
private val PurpleLight = Color(0xFFB23CFF)
private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFB6B1C2)
private val BorderColor = Color(0xFF37384A)
private val ErrorColor = Color(0xFFFF6478)


@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onRememberAccessChange: (Boolean) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onBiometricClick: () -> Unit = {}
) {
    var showPassword by remember {
        mutableStateOf(false)
    }

    var rememberAccess by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundTop,
                        BackgroundBottom
                    )
                )
            )
            .systemBarsPadding()
            .imePadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 22.dp,
                end = 22.dp,
                top = 18.dp,
                bottom = 38.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CompactJBankLogo()
            }

            item {
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            item {
                Text(
                    text = "JBank",
                    color = WhiteText,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(5.dp)
                )
            }

            item {
                Text(
                    text = "Seu banco digital completo,\nsimples, seguro e moderno.",
                    color = SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            item {
                LoginCard(
                    state = state,
                    showPassword = showPassword,
                    rememberAccess = rememberAccess,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onShowPasswordChange = {
                        showPassword = !showPassword
                    },
                    onRememberAccessChange = {
                        rememberAccess = it
                    },
                    onLogin = onLogin
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            item {
                DividerWithText()
            }

            item {
                Spacer(
                    modifier = Modifier.height(18.dp)
                )
            }

            item {
                BiometricButton(
                    onClick = {
                        // Próxima etapa: autenticação biométrica.
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }

            item {
                BenefitsSection()
            }

            item {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            item {
                Text(
                    text = "JBank • Simples • Seguro • Completo",
                    color = SecondaryText,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Composable
private fun CompactJBankLogo() {
    Box(
        modifier = Modifier
            .size(82.dp)
            .clip(
                RoundedCornerShape(23.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        PurpleLight,
                        Purple,
                        Color(0xFF4B0CB7)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "J",
            color = Color.White,
            fontSize = 52.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun LoginCard(
    state: LoginUiState,
    showPassword: Boolean,
    rememberAccess: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowPasswordChange: () -> Unit,
    onRememberAccessChange: (Boolean) -> Unit,
    onLogin: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDark
        ),
        border = BorderStroke(
            width = 1.dp,
            color = BorderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Olá!",
                color = WhiteText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Acesse sua conta para continuar",
                color = SecondaryText,
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
                label = {
                    Text("E-mail")
                },
                placeholder = {
                    Text("Digite seu e-mail")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "E-mail",
                        tint = PurpleLight
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                textStyle = TextStyle(
                    color = WhiteText,
                    fontSize = 20.sp
                ),
                shape = RoundedCornerShape(15.dp),
                colors = loginFieldColors()
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
                label = {
                    Text("Senha")
                },
                placeholder = {
                    Text("Digite sua senha")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Senha",
                        tint = PurpleLight
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = onShowPasswordChange
                    ) {
                        Icon(
                            imageVector = if (showPassword) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = if (showPassword) {
                                "Ocultar senha"
                            } else {
                                "Mostrar senha"
                            },
                            tint = WhiteText
                        )
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                textStyle = TextStyle(
                    color = WhiteText,
                    fontSize = 20.sp
                ),
                shape = RoundedCornerShape(15.dp),
                colors = loginFieldColors()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberAccess,
                    onCheckedChange = onRememberAccessChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Purple,
                        uncheckedColor = PurpleLight,
                        checkmarkColor = Color.White
                    )
                )

                Text(
                    text = "Lembrar meu acesso",
                    color = WhiteText,
                    fontSize = 17.sp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Esqueci minha senha",
                    color = PurpleLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        // Próxima etapa: recuperação de senha.
                    }
                )
            }

            state.errorMessage?.let { message ->
                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = message,
                    color = ErrorColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Button(
                onClick = onLogin,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    contentColor = Color.White,
                    disabledContainerColor = Purple.copy(
                        alpha = 0.55f
                    )
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.size(10.dp)
                    )

                    Text(
                        text = "Entrar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DividerWithText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = BorderColor
        )

        Text(
            text = "ou",
            modifier = Modifier.padding(
                horizontal = 18.dp
            ),
            color = SecondaryText,
            fontSize = 15.sp
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = BorderColor
        )
    }
}

@Composable
private fun BiometricButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = PurpleLight
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = WhiteText
        )
    ) {
        Text(
            text = "◉",
            color = PurpleLight,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.size(12.dp)
        )

        Text(
            text = "Entrar com biometria",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
private fun BenefitsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BenefitItem(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Security,
            title = "Segurança",
            subtitle = "Proteção total"
        )

        BenefitItem(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Bolt,
            title = "Agilidade",
            subtitle = "Tudo na mão"
        )

        BenefitItem(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Diamond,
            title = "Benefícios",
            subtitle = "Vantagens"
        )

        BenefitItem(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.HeadsetMic,
            title = "Suporte",
            subtitle = "Atendimento 24h"
        )
    }
}

@Composable
private fun BenefitItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = PurpleLight,
            modifier = Modifier.size(34.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = title,
            color = WhiteText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = subtitle,
            color = SecondaryText,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
private fun loginFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedContainerColor = SurfaceField,
        unfocusedContainerColor = SurfaceField,
        disabledContainerColor = SurfaceField,

        focusedBorderColor = PurpleLight,
        unfocusedBorderColor = BorderColor,
        disabledBorderColor = BorderColor,

        focusedTextColor = WhiteText,
        unfocusedTextColor = WhiteText,
        disabledTextColor = SecondaryText,

        focusedLabelColor = PurpleLight,
        unfocusedLabelColor = SecondaryText,

        focusedPlaceholderColor = SecondaryText,
        unfocusedPlaceholderColor = SecondaryText,

        cursorColor = PurpleLight
    )