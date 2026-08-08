package com.jucelio.jbankmobile.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val SplashBackgroundTop = Color(0xFF050611)
private val SplashBackgroundBottom = Color(0xFF100025)

private val SplashPurple = Color(0xFF8B2CFF)
private val SplashPurpleLight = Color(0xFFB23CFF)
private val SplashBlue = Color(0xFF327DFF)

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    var showLogo by remember {
        mutableStateOf(false)
    }

    var showSubtitle by remember {
        mutableStateOf(false)
    }

    var showProgress by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        /*
         * Inicia a entrada do J pela esquerda
         * e do Bank pela direita.
         */
        showLogo = true

        /*
         * Aguarda os dois elementos se encontrarem.
         */
        delay(1_000)

        /*
         * Mostra o slogan com fade e escala.
         */
        showSubtitle = true

        delay(300)

        /*
         * Mostra o indicador de carregamento.
         */
        showProgress = true

        /*
         * Tempo para o usuário visualizar a marca.
         */
        delay(1_200)

        /*
         * Libera a navegação para a próxima tela.
         */
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SplashBackgroundTop,
                        SplashBackgroundBottom
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                /*
                 * J entra da esquerda.
                 */
                AnimatedVisibility(
                    visible = showLogo,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth ->
                            -fullWidth * 6
                        },
                        animationSpec = tween(
                            durationMillis = 900,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                ) {
                    JBankLetterJ()
                }

                /*
                 * Bank entra da direita.
                 */
                AnimatedVisibility(
                    visible = showLogo,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth ->
                            fullWidth * 3
                        },
                        animationSpec = tween(
                            durationMillis = 900,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                ) {
                    Text(
                        text = "Bank",
                        color = Color.White,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 600
                    )
                ) + scaleIn(
                    initialScale = 0.90f,
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    )
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Seu banco digital completo",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "Simples • Seguro • Moderno",
                        color = Color.White.copy(
                            alpha = 0.72f
                        ),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(38.dp)
            )

            AnimatedVisibility(
                visible = showProgress,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 400
                    )
                )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(34.dp),
                    color = SplashPurpleLight,
                    trackColor = Color.White.copy(
                        alpha = 0.10f
                    ),
                    strokeWidth = 4.dp
                )
            }
        }
    }
}

@Composable
private fun JBankLetterJ() {
    Box(
        modifier = Modifier
            .size(
                width = 58.dp,
                height = 64.dp
            )
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 28.dp,
                    bottomEnd = 10.dp
                )
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        SplashPurpleLight,
                        SplashPurple,
                        SplashBlue
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "J",
            color = Color.White,
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}