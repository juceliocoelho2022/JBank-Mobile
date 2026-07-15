package com.jucelio.jbankmobile.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PurpleLight = Color(0xFFB23CFF)
private val WhiteText = Color(0xFFF8F5FF)
private val SecondaryText = Color(0xFFB6B1C2)

@Composable
fun LoginBenefits(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        BenefitItem(
            icon = Icons.Default.Security,
            title = "Segurança",
            subtitle = "Proteção total"
        )

        BenefitItem(
            icon = Icons.Default.Bolt,
            title = "Agilidade",
            subtitle = "Tudo na mão"
        )

        BenefitItem(
            icon = Icons.Default.CardGiftcard,
            title = "Benefícios",
            subtitle = "Vantagens"
        )

        BenefitItem(
            icon = Icons.Default.HeadsetMic,
            title = "Suporte",
            subtitle = "Atendimento 24h"
        )
    }
}

@Composable
private fun RowScope.BenefitItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = PurpleLight,
            modifier = Modifier.size(31.dp)
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = title,
            color = WhiteText,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = subtitle,
            color = SecondaryText,
            fontSize = 9.sp,
            lineHeight = 11.sp,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}