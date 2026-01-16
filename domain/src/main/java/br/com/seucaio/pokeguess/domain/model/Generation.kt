package br.com.seucaio.pokeguess.domain.model

enum class Generation(val displayName: String, val offset: Int, val limit: Int) {
    // #1-151 (Kanto)
    I("Generation I", 0, 151),

    // #152-251 (Johto)
    II("Generation II", 151, 100),

    // #252-386 (Hoenn)
    III("Generation III", 251, 135),

    // #387-493 (Sinnoh)
    IV("Generation IV", 386, 107),

    // #494-649 (Unova)
    V("Generation V", 493, 156),

    // #650-721 (Kalos)
    VI("Generation VI", 649, 72),

    // #722-802 (Alola)
    VII("Generation VII", 721, 88),

    // #803-898 (Galar)
    VIII("Generation VIII", 809, 89),

    // #1-898
    ALL("All Generations", I.offset, VIII.limit)
}
