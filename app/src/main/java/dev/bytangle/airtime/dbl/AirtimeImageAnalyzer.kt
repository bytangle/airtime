package dev.bytangle.airtime.dbl

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class AirtimeImageAnalyzer : ImageAnalysis.Analyzer {
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val recognizer = TextRecognition.getClient()

            // recognition block
            recognizer.process(image).addOnSuccessListener { visionText ->
                val resultText = visionText.text;
                Log.d("TextRecognition", resultText)
            }.addOnFailureListener { err ->

            }.addOnCompleteListener {
                imageProxy.close()
            }

        } else {

        }
    }

}