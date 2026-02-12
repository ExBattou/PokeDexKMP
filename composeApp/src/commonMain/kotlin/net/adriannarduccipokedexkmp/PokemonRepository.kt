package net.adriannarduccipokedexkmp

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepository {
    private val client = httpClient()

    fun getPokemonList(limit: Int = 20, offset: Int = 0): Flow<List<PokemonItem>> = flow {
        val response = client.get("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$offset")
        val listResponse: PokemonListResponse = response.body()
        emit(listResponse.results.map {
            val id = extractId(it.url)
            it.copy(url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
        })
    }

    fun getPokemonDetails(name: String): Flow<PokemonDetails> = flow {
        val response = client.get("https://pokeapi.co/api/v2/pokemon/$name")
        val details: PokemonDetails = response.body()
        emit(details)
    }

    private fun extractId(url: String): Int {
        return url.split("/").dropLast(1).last().toInt()
    }
}
