package br.com.seucaio.pokeguess.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.seucaio.pokeguess.core.common.extension.orFalse
import br.com.seucaio.pokeguess.core.common.extension.orZero
import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.domain.repository.GameSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    val context: Context
) : GameSettingsRepository {
    override val gameSettings: Flow<GameSettings> = dataStore.data.map { preferences ->
        GameSettings(
            playerName = preferences[PLAYER_NAME].orEmpty(),
            generation = Generation.getGeneration(preferences[GENERATION]),
            rounds = preferences[ROUNDS].orZero(),
            timerEnabled = preferences[TIMER_ENABLED].orFalse(),
            withFriends = preferences[WITH_FRIENDS].orFalse()
        )
    }


    override suspend fun saveSettings(settings: GameSettings) {
        dataStore.edit { preferences ->
            preferences[PLAYER_NAME] = settings.playerName
            preferences[GENERATION] = settings.generation.name
            preferences[ROUNDS] = settings.rounds
            preferences[TIMER_ENABLED] = settings.timerEnabled
            preferences[WITH_FRIENDS] = settings.withFriends
        }
    }

    override suspend fun clearSettings() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        private val PLAYER_NAME = stringPreferencesKey("player_name")
        private val GENERATION = stringPreferencesKey("generation")
        private val ROUNDS = intPreferencesKey("rounds")
        private val TIMER_ENABLED = booleanPreferencesKey("timer_enabled")
        private val WITH_FRIENDS = booleanPreferencesKey("with_friends")
    }
}
