package br.com.seucaio.pokeguess.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "game_match_players",
    primaryKeys = ["game_id", "player_id"],
    foreignKeys = [
        ForeignKey(
            entity = GameMatchEntity::class,
            parentColumns = ["game_id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["player_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GameMatchPlayerEntity(
    @ColumnInfo(name = "game_id")
    val gameId: Int,
    @ColumnInfo(name = "player_id")
    val playerId: Int,
    val score: Int = 0
)
