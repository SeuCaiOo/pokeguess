package br.com.seucaio.pokeguess.domain.usecase.match

import br.com.seucaio.pokeguess.domain.model.GameMatch
import br.com.seucaio.pokeguess.domain.model.Player
import br.com.seucaio.pokeguess.domain.repository.GameMatchRepository
import br.com.seucaio.pokeguess.domain.repository.PlayerRepository

class StartNewMatchUseCase(
    private val gameMatchRepository: GameMatchRepository,
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(playerNames: List<String>, totalRounds: Int): Int {
        val players = playerNames.map { name ->
            val player = Player(name = name)
            val id = playerRepository.savePlayer(player)
            player.copy(id = id)
        }

        val newMatch = GameMatch(
            players = players,
            totalRounds = totalRounds
        )

        return gameMatchRepository.saveMatch(newMatch)
    }
}
