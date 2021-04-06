package dev.bytangle.airtime.composables.destinations

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import dev.bytangle.airtime.databinding.ScanPreviewBinding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.bytangle.airtime.R
import dev.bytangle.airtime.viewmodels.ScanViewModel

@Composable
fun ScanDestination(
    navHostController : NavHostController,
    activity : ComponentActivity,
    modifier : Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Scan")
                }
            )
        }
    ) {
        // check and request permission
        val permissionGranted = remember { mutableStateOf(false) }
        when {
            ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                permissionGranted.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA) -> {
                // You should use a dialog to display an educational UI
            }

            else -> {
                val reqCode = 6
                requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), reqCode)
                ActivityCompat.OnRequestPermissionsResultCallback { requestCode, permissions, grantResults ->
                    when(requestCode) {
                        reqCode -> {
                            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                permissionGranted.value = true
                            }
                        }
                        else -> {
                            // request other requests
                        }
                    }
                }
            }
        }

        if (permissionGranted.value) {
            ScanBodyContent(modifier = Modifier.padding(it), activity = activity)
        } else {
            // education ui here
            Text(text = "not granted")
        }

    }
}

@Composable
fun ScanBodyContent(modifier : Modifier = Modifier, activity: ComponentActivity) {
    val scanViewModel : ScanViewModel = viewModel()
    Surface(
        modifier = modifier,
        color = colorResource(id = R.color.airtime_bg)
    ) {
        Column() {
            val commonPadding = 8.dp
            Surface(
                modifier = Modifier
                    .padding(start = commonPadding, end = commonPadding, top = commonPadding, bottom = 2.dp)
                    .weight(1F),
                shape = MaterialTheme.shapes.medium,
                elevation = 6.dp,
                color = colorResource(id = R.color.airtime_on_bg)
            ) {
                AndroidViewBinding(
                    factory = ScanPreviewBinding::inflate
                ) {
                    scanViewModel.startScan(
                        activity = activity,
                        viewFinder = scanPreview
                    ) // this start the preview and view analyzer use cases
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Surface(
                modifier = Modifier
                    .padding(start = commonPadding, end = commonPadding, top = 2.dp, bottom = commonPadding)
                    .height(200.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = 6.dp,
                color = colorResource(id = R.color.feedback_bg)
            ) {
                Column(
                ) {
                    Text(
                        "Recharge in progress...",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.ExtraLight,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}