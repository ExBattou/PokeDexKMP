package net.adriannarduccipokedexkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator


@Composable
fun App(cameraView: @Composable (Modifier) -> Unit) {
    MaterialTheme {
        Navigator(MainScreen(cameraView))
    }
}

class MainScreen(private val cameraView: @Composable (Modifier) -> Unit) : Screen {
    @Composable
    override fun Content() {
        val pokemonViewModel: PokemonViewModel = getScreenModel()
        PokedexApp(pokemonViewModel, cameraView)
    }
}
