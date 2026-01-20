package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.repository.GameSettingsRepository
import br.com.seucaio.pokeguess.domain.repository.PlayerRepository

class SaveGameSettingsUseCase(
    private val settingsRepository: GameSettingsRepository,
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(settings: GameSettings) {
        playerRepository.savePlayers(settings.players)
        settingsRepository.saveSettings(settings)
    }
}
