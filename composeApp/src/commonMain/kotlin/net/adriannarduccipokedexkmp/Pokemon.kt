package net.adriannarduccipokedexkmp
import kotlinx.serialization.Serializable


@Serializable
data class PokemonListResponse(
    val results: List<PokemonItem>
)

@Serializable
data class PokemonItem(
    val name: String,
    val url: String // Para extraer ID: url.split("/").penultimate()
)