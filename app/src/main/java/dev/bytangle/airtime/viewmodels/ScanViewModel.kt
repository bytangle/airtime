package dev.bytangle.airtime.viewmodels

import android.util.Log
import android.util.Rational
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

@ExperimentalUseCaseGroup
@ExperimentalUseCaseGroupLifecycle
class ScanViewModel : ViewModel() {
    private lateinit var cameraExecutor : ExecutorService

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
            val viewPort = ViewPort.Builder(Rational(500,500), viewFinder.rotation.toInt()).build()
            val useCaseGroup = UseCaseGroup.Builder()
                .addUseCase(preview)
                .addUseCase(imageAnalyzer)
                .setViewPort(viewPort)
                .build()

            try {
                cameraProvider.unbindAll()
                // bind preview use case and image analyzer use case
                cameraProvider.bindToLifecycle(activity, cameraSelector, useCaseGroup)

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