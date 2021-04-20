package dev.bytangle.airtime.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ComponentActivity
import androidx.navigation.NavHostController
import dev.bytangle.airtime.composables.components.BottomNavBar
import dev.bytangle.airtime.navigation.AirtimeDestination
import dev.bytangle.airtime.navigation.AirtimeNavHost

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun Airtime(
    navHostController: NavHostController,
    airtimeDestinations : List<AirtimeDestination>,
    activity : ComponentActivity,
    modifier : Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navHostController = navHostController, airtimeDestinations = airtimeDestinations.filter { airtimeDestination ->
                airtimeDestination.route != AirtimeDestination.Recharge.route
            })
        }
    ) {
        AirtimeNavHost(
            navHostController = navHostController,
            activity = activity
        )
    }
}