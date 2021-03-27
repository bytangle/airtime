package dev.bytangle.airtime.composables.destinations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.bytangle.airtime.composables.components.Introduce

@Composable
fun SettingsDestination(navHostController: NavHostController, modifier : Modifier = Modifier) {
    Introduce(name = "Settings Destination", modifier = modifier)
}