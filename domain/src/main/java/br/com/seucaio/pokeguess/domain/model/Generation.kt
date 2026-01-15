package br.com.seucaio.pokeguess.domain.model

enum class Generation(val displayName: String, val offset: Int, val limit: Int) {
    I("Generation I", 0, 151),
    II("Generation II", 151, 100),
    III("Generation III", 251, 135),
    IV("Generation IV", 386, 107),
    V("Generation V", 493, 156),
    VI("Generation VI", 649, 72),
    VII("Generation VII", 721, 88),
    VIII("Generation VIII", 809, 89),
    ALL("All Generations", 0, 898)
}
