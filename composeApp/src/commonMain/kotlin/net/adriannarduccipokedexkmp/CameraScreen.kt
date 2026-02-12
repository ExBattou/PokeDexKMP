package net.adriannarduccipokedexkmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

object CameraScreen : Screen {
    @Composable
    override fun Content() {
        CameraView(modifier = Modifier.fillMaxSize())
    }
}
