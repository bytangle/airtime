package dev.bytangle.airtime.dbl

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import dev.bytangle.airtime.utils.AirtimeProcessedResult

class AirtimeImageAnalyzer : ImageAnalysis.Analyzer {
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val recognizer = TextRecognition.getClient()

            // recognition block
            recognizer.process(image).addOnSuccessListener { visionText ->
                // this class handles filtering of text
                val processedResult : AirtimeProcessedResult = AirtimeTextFilter.process(visionText)

                val tag = "AirtimeProcessedResult"
                Log.d(tag, visionText.text)
                Log.d(tag, "Recharge Pin: " + processedResult.rechargePin.toString())
                Log.d(tag, "Pin Prefix: " + processedResult.pinPrefix.toString())
                Log.d(tag, "Amount: " + processedResult.amount.toString())
                Log.d(tag, "Network: " + processedResult.assumedNetwork.name)
            }.addOnFailureListener { err ->

            }.addOnCompleteListener {
                imageProxy.close()
            }

        } else {
            // do something, like inform the user
        }
    }

}