package br.com.seucaio.pokeguess.domain.usecase

import br.com.seucaio.pokeguess.domain.model.NextRoundResult

/** UseCase responsável por gerenciar a progressão dos rounds e verificar fim de jogo. */
class GetNextRoundUseCase {
    operator fun invoke(currentRound: Int, totalRounds: Int): NextRoundResult {
        val nextRound = currentRound + 1
        val isGameOver = nextRound > totalRounds
        return NextRoundResult(nextRound = nextRound, isGameOver = isGameOver)
    }
}
