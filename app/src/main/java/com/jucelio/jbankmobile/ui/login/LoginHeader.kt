package com.jucelio.jbankmobile.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Purple = Color(0xFF8B2CFF)
private val PurpleLight = Color(0xFFB23CFF)
private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFB6B1C2)

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .clip(
                    RoundedCornerShape(24.dp)
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
                fontSize = 54.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "JBank",
            color = WhiteText,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Seu banco digital completo,\nsimples, seguro e moderno.",
            color = SecondaryText,
            fontSize = 14.sp,
            lineHeight = 19.sp,
            textAlign = TextAlign.Center
        )
    }
}