package net.adriannarduccipokedexkmp

import android.app.Application
import org.koin.android.ext.koin.androidContext

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PokemonApplication)
        }
    }
}
