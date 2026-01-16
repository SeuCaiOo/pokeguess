package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.repository.GameSettingsRepository
import kotlinx.coroutines.flow.Flow

class GetGameSettingsUseCase(private val repository: GameSettingsRepository) {
    operator fun invoke(): Result<Flow<GameSettings>> {
        return runCatching { repository.gameSettings }
    }
}
