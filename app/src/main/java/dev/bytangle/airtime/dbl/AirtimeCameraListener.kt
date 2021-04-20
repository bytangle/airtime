package dev.bytangle.airtime.dbl

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.gesture.Gesture
import dev.bytangle.airtime.utils.AirtimeProcessedResult

@ExperimentalMaterialApi
fun processCameraFrames(
    camera : CameraView,
    onAirtimeResult : (airtimeResult : AirtimeProcessedResult) -> Unit
) {
    // add a frame processor for live camera data streaming
    camera.addFrameProcessor { frame ->
        val size = frame.size
        val format = frame.format
        val userRotation = frame.rotationToUser
        val viewRotation = frame.rotationToView

        val data = frame.getData<ByteArray>()

        val image = InputImage.fromByteArray(data, size.width, size.height, userRotation, InputImage.IMAGE_FORMAT_NV21)
        val recognizer = TextRecognition.getClient()
        // perform text recognition here
        performTextRecognition(camera, recognizer, image, onAirtimeResult)
    }
}

@ExperimentalMaterialApi
private fun performTextRecognition(
    camera : CameraView,
    recognizer : TextRecognizer,
    image : InputImage,
    onAirtimeResult : (airtimeResult : AirtimeProcessedResult) -> Unit
) {
    // recognition block
    recognizer.process(image).addOnSuccessListener { visionText ->
        // do not scan and process recognized text if the chunks is not more ten characters
        if (visionText.text.length < 10) {
            return@addOnSuccessListener
        }

        // this class handles filtering of text
        val processedResult : AirtimeProcessedResult = AirtimeTextFilter.process(visionText)

        val tag = "AirtimeProcessedResult"
        Log.d(tag, visionText.text)

        // Navigate to the recharge destination to display result and load recharge card
        if (processedResult.rechargePin.toString().length > 10) {

            // clean camera
            camera.clearGesture(Gesture.TAP)
            camera.clearFocus()
            camera.clearFrameProcessors()
            camera.clearCameraListeners()
            camera.close()
            camera.destroy()

            onAirtimeResult(processedResult)
        }
    }.addOnFailureListener { err ->

    }
}