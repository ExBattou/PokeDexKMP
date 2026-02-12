package net.adriannarduccipokedexkmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVMediaTypeVideo
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraView(modifier: Modifier) {
    val captureSession = remember { AVCaptureSession() }
    val device = remember {
        AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull() as? AVCaptureDevice
    }
    val input = remember {
        device?.let { AVCaptureDeviceInput(device = it, error = null) }
    }
    if (input != null) {
        captureSession.addInput(input)
    }

    val previewLayer = remember {
        AVCaptureVideoPreviewLayer(session = captureSession)
    }

    UIKitView(
        factory = {
            val view = UIView()
            previewLayer.setFrame(view.bounds)
            view.layer.addSublayer(previewLayer)
            captureSession.startRunning()
            view
        },
        onResize = { view, rect ->
            previewLayer.setFrame(rect)
        },
        modifier = modifier
    )
}
