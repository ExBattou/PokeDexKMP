package net.adriannarduccipokedexkmp

import org.koin.dsl.module

val appModule = module {
    factory { PokemonViewModel() }
}
