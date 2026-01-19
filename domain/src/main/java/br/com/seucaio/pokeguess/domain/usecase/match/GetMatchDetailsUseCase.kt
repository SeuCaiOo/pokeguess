package br.com.seucaio.pokeguess.domain.usecase.match

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class GetMatchDetailsUseCase(
    private val repository: GameMatchRepository
) {
    suspend operator fun invoke(matchId: Int): GameMatch? {
        return repository.getMatchById(matchId)
    }
}
