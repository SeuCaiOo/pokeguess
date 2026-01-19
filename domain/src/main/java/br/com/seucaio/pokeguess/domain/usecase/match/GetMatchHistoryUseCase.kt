package br.com.seucaio.pokeguess.domain.usecase.match

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class GetMatchHistoryUseCase(
    private val repository: GameMatchRepository
) {
    suspend operator fun invoke(): List<GameMatch> {
        return repository.getAllMatches()
    }
}
