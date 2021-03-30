package dev.bytangle.airtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import dev.bytangle.airtime.composables.Airtime
import dev.bytangle.airtime.databinding.ScanPreviewBinding
import dev.bytangle.airtime.navigation.AirtimeDestination
import dev.bytangle.airtime.ui.theme.AirtimeTheme
import dev.bytangle.airtime.viewmodels.ScanViewModel
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private val scanVm by viewModels<ScanViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirtimeTheme {
                val navHostController = rememberNavController()
                Airtime(
                    navHostController = navHostController,
                    airtimeDestinations = AirtimeDestination.destinations,
                    activity = this
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

