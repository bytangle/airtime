package dev.bytangle.airtime.viewmodels

import android.content.Context
import android.graphics.PointF
import android.graphics.Rect
import android.location.Location
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ComponentActivity
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModel
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.*
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import com.otaliastudios.cameraview.markers.AutoFocusMarker
import com.otaliastudios.cameraview.markers.AutoFocusTrigger
import com.otaliastudios.cameraview.size.Size
import com.otaliastudios.cameraview.size.SizeSelector
import dev.bytangle.airtime.dbl.AirtimeCameraListener

class ScanViewModel : ViewModel() {
    private var flashLightState : Flash = Flash.OFF
    @RequiresApi(Build.VERSION_CODES.O)
    fun startScanUsingCameraView(activity: ComponentActivity, camera : CameraView) {
        // Configurations
        camera.setLifecycleOwner(activity)
        camera.audio = Audio.OFF
        camera.flash = flashLightState
        camera.engine = Engine.CAMERA2
        camera.frameProcessingPoolSize = 2
        camera.exposureCorrection = 2F
        camera.mode = Mode.PICTURE

        camera.setPreviewStreamSize {
            listOf(it[4])
        }

        camera.isFocusedByDefault = true
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
