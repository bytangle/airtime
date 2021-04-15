package dev.bytangle.airtime.composables.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import dev.bytangle.airtime.R

@Composable
fun ScanOverlay() {
    val color =  colorResource(id = R.color.airtime_tertiary)
    Canvas(modifier = Modifier.fillMaxSize().alpha(5f)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawRoundRect(
            color = color,
            topLeft = Offset(canvasWidth/2, canvasHeight/2),
            size = Size(480f, 360f)
        )

    }
}