package net.adriannarduccipokedexkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator


@Composable
fun App() {
    MaterialTheme {
        Navigator(MainScreen)
    }
}

object MainScreen : Screen {
    @Composable
    override fun Content() {
        val pokemonViewModel: PokemonViewModel = getScreenModel()
        PokedexApp(pokemonViewModel)
    }
}
