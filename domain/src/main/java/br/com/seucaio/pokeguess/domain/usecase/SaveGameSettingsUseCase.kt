package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.repository.GameSettingsRepository

class SaveGameSettingsUseCase(private val repository: GameSettingsRepository) {
    suspend operator fun invoke(settings: GameSettings): Result<Unit> {
        return runCatching { repository.saveSettings(settings) }
    }
}
