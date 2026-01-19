package br.com.seucaio.pokeguess.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.pokeguess.data.local.database.entity.RoundEntity
import br.com.seucaio.pokeguess.domain.model.Pokemon

@Dao
interface RoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(round: RoundEntity): Long

    @Query(
        """
        SELECT r.*, p.name as pokemon_name, p.image_url as pokemon_image_url 
        FROM rounds r
        INNER JOIN pokemons p ON r.pokemon_id = p.id
        WHERE r.game_id = :gameId 
        ORDER BY r.round_number ASC
    """
    )
    suspend fun getRoundsWithPokemonByGameId(gameId: Int): List<RoundWithPokemon>
}

data class RoundWithPokemon(
    val round_id: Int,
    val game_id: Int,
    val round_number: Int,
    val user_guess: String,
    val is_correct: Boolean,
    val pokemon_id: Int,
    val pokemon_name: String,
    val pokemon_image_url: String
) {
    fun toPokemon(): Pokemon {
        return Pokemon(
            id = pokemon_id,
            name = pokemon_name,
            imageUrl = pokemon_image_url
        )
    }
}
