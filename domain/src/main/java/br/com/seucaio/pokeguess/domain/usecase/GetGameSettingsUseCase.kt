package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.domain.repository.GameSettingsRepository
import br.com.seucaio.pokeguess.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGameSettingsUseCase(
    private val repository: GameSettingsRepository,
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(): Flow<GameSettings> {
        return repository.gameSettings.map {
            it.addPlayers(playerRepository.getPlayerByNames(it.playerNames))
        }
    }
}
