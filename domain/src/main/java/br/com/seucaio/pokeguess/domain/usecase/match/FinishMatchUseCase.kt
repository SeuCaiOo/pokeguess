package br.com.seucaio.pokeguess.domain.usecase.match

import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class FinishMatchUseCase(
    private val repository: GameMatchRepository
) {
    suspend operator fun invoke(matchId: Int) {
        repository.finishMatch(matchId)
    }
}
