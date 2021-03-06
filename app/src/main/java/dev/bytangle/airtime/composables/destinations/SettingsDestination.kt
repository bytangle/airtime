package dev.bytangle.airtime.composables.destinations

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.bytangle.airtime.R
import dev.bytangle.airtime.composables.components.Introduce

@Composable
fun SettingsDestination(navHostController: NavHostController, modifier : Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontSize = 26.sp,
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)
                        ))
                    )
                },
                backgroundColor = colorResource(id = R.color.airtime_bar_bg)
            )
        }
    ) {

    }
}