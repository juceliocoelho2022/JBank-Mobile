package com.jucelio.jbankmobile.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PurpleLight = Color(0xFFB23CFF)
private val WhiteText = Color(0xFFF8F5FF)

@Composable
fun BiometricButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = PurpleLight
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = WhiteText
        )
    ) {
        Icon(
            imageVector = Icons.Default.Fingerprint,
            contentDescription = "Biometria",
            tint = PurpleLight
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = "Entrar com biometria",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}