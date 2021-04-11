package dev.bytangle.airtime.viewmodels

import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Flash
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import dev.bytangle.airtime.dbl.AirtimeCameraListener

class ScanViewModel : ViewModel() {
    private var flashLightState : Flash = Flash.OFF
    fun startScanUsingCameraView(activity: ComponentActivity, camera : CameraView) {
        // Configurations
        camera.setLifecycleOwner(activity)
        camera.audio = Audio.OFF
        camera.flash = flashLightState

        // Gesture setup
        camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)

        // assign listener to handle the rest of the logic
        camera.addCameraListener(AirtimeCameraListener(camera))
    }

    fun turnOnCameraFlashLight() {
        flashLightState = Flash.ON
    }

    fun turnOffCameraFlashLight() {
       flashLightState = Flash.TORCH
    }
}