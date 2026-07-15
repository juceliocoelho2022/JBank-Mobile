package com.jucelio.jbankmobile.ui.pix

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.atomic.AtomicBoolean

class QrCodeAnalyzer(
    private val scanner: BarcodeScanner,
    private val onQrCodeDetected: (String) -> Unit,
    private val onFailure: (Throwable) -> Unit
) : ImageAnalysis.Analyzer {

    private val isProcessing = AtomicBoolean(false)
    private val hasDetectedCode = AtomicBoolean(false)

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (
            isProcessing.get() ||
            hasDetectedCode.get()
        ) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image

        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        isProcessing.set(true)

        val inputImage = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                val qrValue = barcodes
                    .firstOrNull()
                    ?.rawValue
                    ?.trim()

                if (
                    !qrValue.isNullOrBlank() &&
                    hasDetectedCode.compareAndSet(
                        false,
                        true
                    )
                ) {
                    onQrCodeDetected(qrValue)
                }
            }
            .addOnFailureListener { error ->
                onFailure(error)
            }
            .addOnCompleteListener {
                isProcessing.set(false)
                imageProxy.close()
            }
    }
}