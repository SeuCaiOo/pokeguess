package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.NextRoundResult

class GetNextRoundUseCase {
    operator fun invoke(currentRound: Int, totalRounds: Int): NextRoundResult {
        val isGameOver = currentRound + 1 == totalRounds
        val nextRound = if (isGameOver) currentRound else currentRound + 1
        return NextRoundResult(nextRound = nextRound, isGameOver = isGameOver)
    }
}
