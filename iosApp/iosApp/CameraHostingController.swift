
import SwiftUI

class CameraHostingController: UIHostingController<CameraView> {
    
    init() {
        super.init(rootView: CameraView())
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder, rootView: CameraView())
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
