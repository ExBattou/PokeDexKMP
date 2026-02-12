package net.adriannarduccipokedexkmp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetails(
    val name: String,
    val id: Int,
    val sprites: Sprites,
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>? = null
)

@Serializable
data class Sprites(
    @SerialName("front_default") val frontDefault: String
)

@Serializable
data class FlavorTextEntry(
    @SerialName("flavor_text") val flavorText: String,
    val language: Language
)

@Serializable
data class Language(
    val name: String
)