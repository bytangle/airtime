package dev.bytangle.airtime.viewmodels

import android.util.Log
import android.util.Rational
import androidx.annotation.WorkerThread
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dev.bytangle.airtime.dbl.AirtimeImageAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.frame.FrameProcessor
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import dev.bytangle.airtime.dbl.AirtimeCameraListener

class ScanViewModel : ViewModel() {
    private lateinit var cameraExecutor : ExecutorService
    
    fun startScanUsingCameraView(activity: ComponentActivity, camera : CameraView) {
        // Configurations
        camera.setLifecycleOwner(activity)
        camera.audio = Audio.OFF

        // Gesture setup
        camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)

        // assign listener to handle the rest of the logic
        camera.addCameraListener(AirtimeCameraListener(camera))
    }

    fun startScan(activity: ComponentActivity, viewFinder : PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            // preview use case
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.createSurfaceProvider())
            }

            // imageAnalyzer use case
            cameraExecutor = Executors.newSingleThreadExecutor();
            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(cameraExecutor, AirtimeImageAnalyzer())
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                // bind preview use case and image analyzer use case
                cameraProvider.bindToLifecycle(activity, cameraSelector, preview, imageAnalyzer)

            } catch (err : Exception) {
                Log.e("AirtimeCameraX", "Use case binding failed", err)
            }
        }, ContextCompat.getMainExecutor(activity))
    }

    override fun onCleared() {
        super.onCleared()
        cameraExecutor.shutdown()
    }

}