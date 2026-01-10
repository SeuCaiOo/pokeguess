package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class GetLastMatchUseCase(private val gameMatchRepository: GameMatchRepository) {
    suspend operator fun invoke(): GameMatch? {
        return gameMatchRepository.getLastMatch()
    }
}
