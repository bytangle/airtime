package dev.bytangle.artime.composables

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.bytangle.artime.composables.components.BottomNavBar
import dev.bytangle.artime.navigation.AirtimeDestination
import dev.bytangle.artime.navigation.AirtimeNavHost

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