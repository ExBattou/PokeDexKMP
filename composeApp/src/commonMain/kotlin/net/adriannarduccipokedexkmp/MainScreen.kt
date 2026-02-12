package net.adriannarduccipokedexkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage // Para cargar imágenes

@Composable
fun PokedexApp(viewModel: PokemonViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    // Esto se ejecuta solo una vez cuando se compone la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadPokemonList()
    }
    val pokemonList by viewModel.pokemonList.collectAsState()
    val selectedPokemon by viewModel.selectedPokemon.collectAsState()
    val imageLoader = rememberImageLoader()

    Scaffold { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(pokemonList) { pokemon ->
                PokemonListItem(pokemon, imageLoader) {
                    if (pokemon.name.lowercase() == "squirtle") {
                        navigator.push(CameraScreen)
                    } else {
                        viewModel.loadDetails(pokemon.name)
                    }
                }
            }
        }
    }

    selectedPokemon?.let { details ->
        AlertDialog( // O usa una nueva Screen con Navigation si preferís
            onDismissRequest = { viewModel.clearSelectedPokemon() },
            title = { Text(details.name.uppercase() + " #${details.id}") },
            text = {
                Column {
                    AsyncImage(
                        model = details.sprites.frontDefault,
                        contentDescription = details.name,
                        imageLoader = imageLoader,
                        modifier = Modifier.size(200.dp).background(Color.LightGray)
                    )
                    Text(getDescription(details)) // Función helper abajo
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.clearSelectedPokemon() }) {
                    Text("Cerrar")
                }
            },
        )
    }
}

@Composable
fun PokemonListItem(pokemon: PokemonItem, imageLoader: coil3.ImageLoader, onClick: () -> Unit) {
    Row(modifier = Modifier.clickable(onClick = onClick).padding(16.dp)) {
        AsyncImage(
            model = pokemon.url,
            contentDescription = pokemon.name,
            imageLoader = imageLoader,
            modifier = Modifier.size(64.dp).background(Color.LightGray)
        )

        Spacer(Modifier.width(16.dp).background(Color.Red))
        Column {
            Text(pokemon.name.uppercase())
            Text("#${extractId(pokemon.url)}")
        }
    }
}

private fun getDescription(details: PokemonDetails): String {
    return details.flavorTextEntries?.firstOrNull { it.language.name == "en" }?.flavorText ?: "No description available."
}

private fun extractId(url: String): Int {
    // Updated to extract the ID from the image URL like:
    // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png
    return url.substringAfterLast('/').substringBeforeLast('.').toIntOrNull() ?: 0
}
