package net.adriannarduccipokedexkmp

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PokemonViewModel {
    private val repository = PokemonRepository()
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + handler)

    private val _pokemonList = MutableStateFlow<List<PokemonItem>>(emptyList())
    val pokemonList: StateFlow<List<PokemonItem>> = _pokemonList.asStateFlow()

    private val _selectedPokemon = MutableStateFlow<PokemonDetails?>(null)
    val selectedPokemon: StateFlow<PokemonDetails?> = _selectedPokemon.asStateFlow()

    // ← Agregamos esta función pública
    fun selectPokemon(details: PokemonDetails?) {
        _selectedPokemon.value = details
    }

    // ← Agrega esta función pública para limpiar
    fun clearSelectedPokemon() {
        _selectedPokemon.value = null
    }

    fun loadPokemonList() {
        scope.launch {
            repository.getPokemonList().collectLatest { list ->
                _pokemonList.value = list
            }
        }
    }

    fun loadDetails(name: String) {
        scope.launch {
            repository.getPokemonDetails(name).collectLatest { details ->
                _selectedPokemon.value = details
            }
        }
    }
}