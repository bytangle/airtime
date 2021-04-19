package dev.bytangle.airtime.dbl

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.gesture.Gesture
import dev.bytangle.airtime.utils.AirtimeProcessedResult

class AirtimeCameraListener(
    private val camera : CameraView
) : CameraListener() {
    override fun onCameraOpened(options: CameraOptions) {
        super.onCameraOpened(options)
        startCameraFramesProcessing()
    }

    override fun onCameraClosed() {
        super.onCameraClosed()
        camera.clearGesture(Gesture.TAP)
        camera.clearFocus()
        camera.clearFrameProcessors()
        camera.clearCameraListeners()
        camera.close()
    }

    private fun startCameraFramesProcessing() {
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
            performTextRecognition(recognizer, image)
        }
    }

    private fun performTextRecognition(recognizer : TextRecognizer, image : InputImage) {
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

        }
    }
}