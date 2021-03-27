package dev.bytangle.airtime.composables.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Introduce(name : String, modifier : Modifier = Modifier) {
    Text(text = "I am $name", style = MaterialTheme.typography.h4, modifier = modifier)
}