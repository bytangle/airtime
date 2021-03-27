package dev.bytangle.airtime.composables.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import dev.bytangle.airtime.navigation.AirtimeDestination

@Composable
fun BottomNavBar(navHostController: NavHostController, airtimeDestinations : List<AirtimeDestination>, modifier : Modifier = Modifier) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        
        airtimeDestinations.forEach { airtimeDestination ->  
            BottomNavigationItem(
                icon = { Icon(imageVector = airtimeDestination.icon, contentDescription = stringResource(
                    id = airtimeDestination.resourceId
                )) },
                label = { Text(stringResource(id = airtimeDestination.resourceId)) },
                selected = currentRoute == airtimeDestination.route,
                onClick = {
                    navHostController.navigate(airtimeDestination.route) {
                        popUpTo =navHostController.graph.startDestination
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}