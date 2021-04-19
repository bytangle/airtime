package dev.bytangle.airtime.composables.destinations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.bytangle.airtime.composables.components.Introduce

@Composable
fun RechargeDestination(modifier : Modifier = Modifier, navHostController : NavHostController) {
    Introduce(name = "RechargeDestination")
}