package com.jucelio.jbankmobile.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jucelio.jbankmobile.R
import kotlinx.coroutines.delay

private val SplashBackgroundTop = Color(0xFF050611)
private val SplashBackgroundBottom = Color(0xFF100025)
private val SplashPurple = Color(0xFF8B2CFF)
private val SplashPurpleLight = Color(0xFFB23CFF)

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
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
            )
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.imagen_principal
                ),
                contentDescription = "Logo do JBank",
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .clip(
                        RoundedCornerShape(32.dp)
                    ),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = "Seu banco digital completo",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Simples • Seguro • Moderno",
                color = Color.White.copy(
                    alpha = 0.72f
                ),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(34.dp)
            )

            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                color = SplashPurpleLight,
                strokeWidth = 2.dp
            )
        }
    }
}