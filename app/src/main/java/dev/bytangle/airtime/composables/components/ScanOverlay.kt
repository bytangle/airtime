package dev.bytangle.airtime.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import dev.bytangle.airtime.R

@Composable
fun ScanOverlay() {
    Surface(
        color = colorResource(id = R.color.airtime_tertiary),
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {

    }
}