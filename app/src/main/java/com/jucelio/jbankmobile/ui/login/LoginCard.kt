package com.jucelio.jbankmobile.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SurfaceDark = Color(0xFF101220)
private val SurfaceField = Color(0xFF0D0F1C)
private val Purple = Color(0xFF8B2CFF)
private val PurpleLight = Color(0xFFB23CFF)
private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFB6B1C2)
private val BorderColor = Color(0xFF37384A)
private val ErrorColor = Color(0xFFFF6478)

@Composable
fun LoginCard(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberAccessChange: (Boolean) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDark
        ),
        border = BorderStroke(
            width = 1.dp,
            color = BorderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Olá!",
                color = WhiteText,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Acesse sua conta para continuar",
                color = SecondaryText,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                modifier = Modifier.fillMaxWidth(),
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(
                    color = WhiteText,
                    fontSize = 15.sp
                ),
                colors = loginFieldColors()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
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
                        onClick = onTogglePasswordVisibility
                    ) {
                        Icon(
                            imageVector = if (
                                state.isPasswordVisible
                            ) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = if (
                                state.isPasswordVisible
                            ) {
                                "Ocultar senha"
                            } else {
                                "Mostrar senha"
                            },
                            tint = WhiteText
                        )
                    }
                },
                visualTransformation = if (
                    state.isPasswordVisible
                ) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(
                    color = WhiteText,
                    fontSize = 15.sp
                ),
                colors = loginFieldColors()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.rememberAccess,
                    onCheckedChange = onRememberAccessChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Purple,
                        uncheckedColor = PurpleLight,
                        checkmarkColor = Color.White
                    )
                )

                Text(
                    text = "Lembrar meu acesso",
                    modifier = Modifier.weight(1f),
                    color = WhiteText,
                    fontSize = 12.sp
                )

                Text(
                    text = "Esqueci minha senha",
                    color = PurpleLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(
                        onClick = onForgotPasswordClick
                    )
                )
            }

            state.errorMessage?.let { message ->
                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = message,
                    color = ErrorColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Button(
                onClick = onLoginClick,
                enabled = state.canLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    contentColor = Color.White,
                    disabledContainerColor = Purple.copy(
                        alpha = 0.45f
                    ),
                    disabledContentColor = Color.White.copy(
                        alpha = 0.65f
                    )
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.padding(
                            horizontal = 5.dp
                        )
                    )

                    Text(
                        text = "Entrar",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
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