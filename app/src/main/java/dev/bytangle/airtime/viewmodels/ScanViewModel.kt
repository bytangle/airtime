package dev.bytangle.airtime.viewmodels

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService

class ScanViewModel : ViewModel() {
    fun startScan(activity: ComponentActivity, viewFinder : PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.createSurfaceProvider())
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                // bind preview use case
                cameraProvider.bindToLifecycle(activity, cameraSelector, preview)

            } catch (err : Exception) {
                Log.e("ArtimeCameraX", "Use case binding failed", err)
            }
        }, ContextCompat.getMainExecutor(activity))
    }

}