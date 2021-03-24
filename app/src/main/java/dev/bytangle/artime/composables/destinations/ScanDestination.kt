package dev.bytangle.artime.composables.destinations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.bytangle.artime.composables.components.Introduce

@Composable
fun ScanDestination(navHostController : NavHostController, modifier : Modifier = Modifier) {
    Introduce(name = "Scan Destination", modifier = modifier)
}