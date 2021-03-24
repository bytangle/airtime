package dev.bytangle.artime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.bytangle.artime.composables.Airtime
import dev.bytangle.artime.navigation.AirtimeDestination
import dev.bytangle.artime.ui.theme.AirtimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirtimeTheme {
                val navHostController = rememberNavController()
                Airtime(
                    navHostController = navHostController,
                    airtimeDestinations = AirtimeDestination.destinations
                )
            }
        }
    }
}

@Preview
@Composable
fun AirtimePreview() {
    Airtime(navHostController = rememberNavController(), airtimeDestinations = AirtimeDestination.destinations)
}
