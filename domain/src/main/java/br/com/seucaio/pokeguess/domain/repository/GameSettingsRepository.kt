package br.com.seucaio.pokeguess.domain.repository

import br.com.seucaio.pokeguess.domain.model.GameSettings
import kotlinx.coroutines.flow.Flow

interface GameSettingsRepository {
    val gameSettings: Flow<GameSettings>
    suspend fun saveSettings(settings: GameSettings)
}
