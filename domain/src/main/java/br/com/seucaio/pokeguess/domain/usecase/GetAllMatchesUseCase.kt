package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository

class GetAllMatchesUseCase(private val repository: GameMatchRepository) {
    suspend operator fun invoke(): Result<List<GameMatch>> {
        return runCatching { repository.getAllMatches() }
    }
}
