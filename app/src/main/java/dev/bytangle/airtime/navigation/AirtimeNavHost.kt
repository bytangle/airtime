package dev.bytangle.airtime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.bytangle.airtime.composables.destinations.ScanDestination
import dev.bytangle.airtime.composables.destinations.SettingsDestination
import dev.bytangle.airtime.composables.destinations.UsageDestination

@Composable
fun AirtimeNavHost(navHostController : NavHostController) {
   NavHost(navController = navHostController, startDestination = AirtimeDestination.Usage.route) {
       composable(AirtimeDestination.Usage.route) { UsageDestination(navHostController = navHostController)}
       composable(AirtimeDestination.Settings.route) { SettingsDestination(navHostController = navHostController)}
       composable(AirtimeDestination.Scan.route) { ScanDestination(navHostController = navHostController)}
   }
}