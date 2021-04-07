package dev.bytangle.airtime.composables.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import dev.bytangle.airtime.R
import dev.bytangle.airtime.navigation.AirtimeDestination

@Composable
fun BottomNavBar(navHostController: NavHostController, airtimeDestinations : List<AirtimeDestination>, modifier : Modifier = Modifier) {
    BottomNavigation(
        //modifier = modifier.height(80.dp),
        backgroundColor = colorResource(id = R.color.airtime_bar_bg)
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        
        airtimeDestinations.forEach { airtimeDestination ->  
            BottomNavigationItem(
                icon = { Icon(
                    imageVector = airtimeDestination.icon,
                    contentDescription = stringResource(
                        id = airtimeDestination.resourceId
                    ),
                    tint = colorResource(id = R.color.airtime_primary)
                ) },
                label = { Text(
                    text = stringResource(id = airtimeDestination.resourceId),
                    color = colorResource(id = R.color.airtime_primary),
                    fontFamily = FontFamily(listOf(
                        Font(R.font.sourcesanspro_light)
                    ))
                ) },
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