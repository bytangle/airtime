package dev.bytangle.airtime.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.*
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import dev.bytangle.airtime.dbl.processCameraFrames
import dev.bytangle.airtime.navigation.AirtimeDestination
import dev.bytangle.airtime.utils.AirtimeProcessedResult
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class ScanViewModel : ViewModel() {

    var scanBottomSheetScaffoldState = BottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
        snackbarHostState = SnackbarHostState(),
        drawerState = DrawerState(DrawerValue.Closed)
    )

    private var flashLightState : Flash = Flash.OFF

    @RequiresApi(Build.VERSION_CODES.O)
    fun startScanUsingCameraView(activity: ComponentActivity, camera : CameraView, navHostController : NavHostController) {
        // Configurations
        camera.setLifecycleOwner(activity)
        camera.audio = Audio.OFF
        camera.flash = flashLightState
        camera.engine = Engine.CAMERA2
        camera.frameProcessingPoolSize = 1
        camera.exposureCorrection = 2F
        camera.mode = Mode.PICTURE

        camera.setPreviewStreamSize {
            listOf(it[4])
        }

        camera.isFocusedByDefault = true
        // Gesture setup
        camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)

        // handle camera frames
        // the frames are processed and pin extracted
        // the process result is obtained through lambda function
        processCameraFrames(
            camera
        ) { processedResult ->
            val tag = "AirtimeProcessedResult"
            Log.d(tag, "Recharge Pin: " + processedResult.rechargePin.toString())
            Log.d(tag, "Pin Prefix: " + processedResult.pinPrefix.toString())
            Log.d(tag, "Amount: " + processedResult.amount.toString())
            Log.d(tag, "Network: " + processedResult.assumedNetwork.name)

            val airtimeInfo = "${processedResult.rechargePin},${processedResult.pinPrefix},${processedResult.amount},${processedResult.assumedNetwork.name}"
            navHostController.navigate(AirtimeDestination.Recharge.route + "/$airtimeInfo")
        }

    }

    private fun handleAirtimeProcessedResult(processedResult: AirtimeProcessedResult) {
        viewModelScope.launch {
            scanBottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    fun turnOnCameraFlashLight() {
        flashLightState = Flash.ON
    }

    fun turnOffCameraFlashLight() {
       flashLightState = Flash.TORCH
    }
}
