package br.com.seucaio.pokeguess.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.seucaio.pokeguess.data.local.Converters
import br.com.seucaio.pokeguess.data.local.database.dao.GameMatchDao
import br.com.seucaio.pokeguess.data.local.database.dao.PokemonDao
import br.com.seucaio.pokeguess.data.local.database.entity.GameMatchEntity
import br.com.seucaio.pokeguess.data.local.database.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class, GameMatchEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PokeGuessDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun gameMatchDao(): GameMatchDao

    companion object {
        @Volatile
        private var INSTANCE: PokeGuessDatabase? = null
        const val DATABASE_NAME = "pokeguess_app_database"

        fun getDatabase(context: Context): PokeGuessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = PokeGuessDatabase::class.java,
                    name = DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
