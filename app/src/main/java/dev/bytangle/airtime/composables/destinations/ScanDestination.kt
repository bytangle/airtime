package dev.bytangle.airtime.composables.destinations

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import dev.bytangle.airtime.databinding.ScanPreviewBinding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.bytangle.airtime.R
import dev.bytangle.airtime.viewmodels.ScanViewModel

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScanDestination(
    navHostController : NavHostController,
    activity : ComponentActivity,
    modifier : Modifier = Modifier
) {
    val scanViewModel : ScanViewModel = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Scan",
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)
                        )),
                        fontSize = 26.sp
                    )
                },
                backgroundColor = colorResource(id = R.color.airtime_bar_bg)
            )
        },
        floatingActionButton = {
            val icon = remember { mutableStateOf(Icons.Filled.FlashlightOn)}
            FloatingActionButton(
                onClick = {
                    if (icon.value == Icons.Filled.FlashlightOn) {
                        icon.value = Icons.Filled.FlashlightOff
                        scanViewModel.turnOnCameraFlashLight()
                    } else {
                        icon.value = Icons.Filled.FlashlightOn
                        scanViewModel.turnOffCameraFlashLight()
                    }
                },
                backgroundColor = colorResource(id = R.color.airtime_tertiary),
                contentColor = MaterialTheme.colors.onSurface,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 46.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp, pressedElevation = 10.dp),
            ) {
                Icon(imageVector = icon.value, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        // check and request permission
        val permissionGranted = remember { mutableStateOf(false) }
        when {
            ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                permissionGranted.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
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
            ScanBodyContent(modifier = Modifier.padding(it), activity = activity, scanViewModel = scanViewModel, navHostController = navHostController)
        } else {
            // education ui here
            Text(text = "not granted")
        }

    }
}

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScanBodyContent(modifier : Modifier = Modifier, activity: ComponentActivity, scanViewModel : ScanViewModel, navHostController : NavHostController) {
    Surface(
        modifier = modifier,
        color = colorResource(id = R.color.blue_bg)
    ) {
        Column() {
            val commonPadding = 10.dp
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 2.dp,
                modifier = Modifier
                    .padding(
                        start = commonPadding,
                        end = commonPadding,
                        top = commonPadding,
                        bottom = commonPadding
                    )
                    .weight(1F),
                color = colorResource(id = R.color.airtime_bg)
            ) {
                AndroidViewBinding(
                    factory = ScanPreviewBinding::inflate
                ) {
                    scanViewModel.startScanUsingCameraView(
                        activity = activity,
                        camera = camera,
                        navHostController = navHostController
                    ) // this start the preview and view analyzer use cases
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}