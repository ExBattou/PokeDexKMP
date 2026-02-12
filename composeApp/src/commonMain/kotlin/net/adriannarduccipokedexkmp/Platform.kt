package net.adriannarduccipokedexkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform