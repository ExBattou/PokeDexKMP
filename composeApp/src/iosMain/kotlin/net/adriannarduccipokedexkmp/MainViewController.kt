package net.adriannarduccipokedexkmp

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    App(
        cameraView = { modifier: Modifier ->
            CameraView(modifier = modifier)
        }
    )
}
