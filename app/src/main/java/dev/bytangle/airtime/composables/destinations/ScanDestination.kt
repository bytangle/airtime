package dev.bytangle.airtime.composables.destinations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavHostController
import dev.bytangle.airtime.databinding.ScanPreviewBinding

@Composable
fun ScanDestination(navHostController : NavHostController, modifier : Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Scan")
                }
            )
        }
    ) {
        ScanBodyContent(modifier = Modifier.padding(it))
    }
}

@Composable
fun ScanBodyContent(modifier : Modifier = Modifier) {
    Column(modifier = modifier) {
        AndroidViewBinding(ScanPreviewBinding::inflate)
    }
}