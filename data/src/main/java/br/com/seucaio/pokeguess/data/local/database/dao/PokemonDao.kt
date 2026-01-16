package br.com.seucaio.pokeguess.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons WHERE id = :pokemonId")
    suspend fun getById(pokemonId: Int): PokemonEntity?

    @Query("SELECT * FROM pokemons")
    suspend fun getAll(): List<PokemonEntity>

    @Query("SELECT NOT EXISTS(SELECT 1 FROM pokemons)")
    suspend fun isEmpty(): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM pokemons)")
    suspend fun hasData(): Boolean

    @Query("SELECT * FROM pokemons WHERE id > :offset LIMIT :limit")
    suspend fun getByGeneration(offset: Int, limit: Int): List<PokemonEntity>

    @Query("DELETE FROM pokemons")
    suspend fun deleteAll()
}
