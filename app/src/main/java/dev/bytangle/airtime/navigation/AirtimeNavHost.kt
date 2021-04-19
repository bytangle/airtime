package dev.bytangle.airtime.navigation

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.bytangle.airtime.composables.destinations.RechargeDestination
import dev.bytangle.airtime.composables.destinations.ScanDestination
import dev.bytangle.airtime.composables.destinations.SettingsDestination
import dev.bytangle.airtime.composables.destinations.UsageDestination

@Composable
fun AirtimeNavHost(
    navHostController : NavHostController,
    activity : ComponentActivity
) {
   NavHost(navController = navHostController, startDestination = AirtimeDestination.Usage.route) {
       composable(AirtimeDestination.Usage.route) { UsageDestination(navHostController = navHostController)}
       composable(AirtimeDestination.Settings.route) { SettingsDestination(navHostController = navHostController)}
       composable(AirtimeDestination.Scan.route) { ScanDestination(
           navHostController = navHostController,
           activity = activity
       )}
       composable(AirtimeDestination.Recharge.route) {
           RechargeDestination(navHostController = navHostController)
       }
   }
}