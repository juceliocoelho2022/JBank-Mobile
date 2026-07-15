package com.jucelio.jbankmobile.ui.pix

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun QrScannerOverlay(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val frameSize = size.width * 0.72f

        val left = (size.width - frameSize) / 2f
        val top = (size.height - frameSize) / 2f

        drawRoundRect(
            color = Color.White,
            topLeft = Offset(
                x = left,
                y = top
            ),
            size = Size(
                width = frameSize,
                height = frameSize
            ),
            cornerRadius = CornerRadius(
                x = 24.dp.toPx(),
                y = 24.dp.toPx()
            ),
            style = Stroke(
                width = 4.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(
                        24f,
                        14f
                    )
                )
            )
        )
    }
}