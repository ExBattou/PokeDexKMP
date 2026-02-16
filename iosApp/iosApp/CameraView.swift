
import SwiftUI
import AVFoundation

struct CameraView: View {
    @StateObject private var cameraController = CameraController()

    var body: some View {
        CameraPreview(cameraController: cameraController)
            .onAppear {
                cameraController.startSession()
            }
            .onDisappear {
                cameraController.stopSession()
            }
    }
}

class CameraController: ObservableObject {
    var session = AVCaptureSession()
    private var videoDeviceInput: AVCaptureDeviceInput?
    @Published var isSessionRunning = false

    func startSession() {
        guard !isSessionRunning else { return }

        session.beginConfiguration()
        session.sessionPreset = .photo

        guard let videoDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back),
              let videoDeviceInput = try? AVCaptureDeviceInput(device: videoDevice) else {
            print("Failed to get the camera device")
            session.commitConfiguration()
            return
        }

        if session.canAddInput(videoDeviceInput) {
            session.addInput(videoDeviceInput)
            self.videoDeviceInput = videoDeviceInput
        }

        session.commitConfiguration()

        DispatchQueue.global(qos: .background).async {
            self.session.startRunning()
            DispatchQueue.main.async {
                self.isSessionRunning = true
            }
        }
    }

    func stopSession() {
        guard isSessionRunning else { return }
        DispatchQueue.global(qos: .background).async {
            self.session.stopRunning()
            DispatchQueue.main.async {
                self.isSessionRunning = false
                if let input = self.videoDeviceInput {
                    self.session.removeInput(input)
                    self.videoDeviceInput = nil
                }
            }
        }
    }
}

struct CameraPreview: UIViewControllerRepresentable {
    @ObservedObject var cameraController: CameraController

    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = UIViewController()
        let previewLayer = AVCaptureVideoPreviewLayer(session: cameraController.session)
        previewLayer.videoGravity = .resizeAspectFill
        viewController.view.layer.addSublayer(previewLayer)
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        if let previewLayer = uiViewController.view.layer.sublayers?.first as? AVCaptureVideoPreviewLayer {
            previewLayer.frame = uiViewController.view.bounds
        }
    }
}
