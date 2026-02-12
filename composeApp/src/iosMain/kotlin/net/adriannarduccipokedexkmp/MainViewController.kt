package net.adriannarduccipokedexkmp

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = {
        // You can add any additional configuration here
    }
) {
    initKoin()
    App()
}
