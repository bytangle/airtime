package dev.bytangle.airtime.composables

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.bytangle.airtime.composables.components.BottomNavBar
import dev.bytangle.airtime.navigation.AirtimeDestination
import dev.bytangle.airtime.navigation.AirtimeNavHost

@Composable
fun Airtime(
    navHostController: NavHostController,
    airtimeDestinations : List<AirtimeDestination>,
    modifier : Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navHostController = navHostController, airtimeDestinations = airtimeDestinations)
        }
    ) {
        AirtimeNavHost(navHostController = navHostController)
    }
}