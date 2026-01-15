package br.com.seucaio.pokeguess.domain.model

data class AdvanceRoundResult(
    val nextRound: Int,
    val isGameOver: Boolean,
    val nextPokemon: Pokemon? = null
)
