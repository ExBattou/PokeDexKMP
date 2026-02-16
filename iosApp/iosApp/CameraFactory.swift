
import Foundation
import UIKit

@objc public class CameraFactory: NSObject {
    @objc public func createCameraViewController() -> UIViewController {
        return CameraHostingController()
    }
}
