package br.com.seucaio.pokeguess.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.seucaio.pokeguess.domain.model.GameSettings
import br.com.seucaio.pokeguess.features.game.GameScreen
import br.com.seucaio.pokeguess.features.history.HistoryScreen
import br.com.seucaio.pokeguess.features.home.HomeScreen
import br.com.seucaio.pokeguess.features.menu.MenuScreen
import br.com.seucaio.pokeguess.features.pokemons.PokemonScreen
import br.com.seucaio.pokeguess.features.score.ScoreScreen

@Composable
fun PokeGuessNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBackButton =
        navBackStackEntry != null && navController.previousBackStackEntry != null

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PokeGuessRoute.Home
    ) {
        homeScreen(navController)
        historyScreen(navController)
        menuScreen(navController)
        pokemonScreen(navController)
        gameScreen(navController)
        scoreScreen(navController)
    }
}

private fun NavGraphBuilder.homeScreen(navController: NavHostController) {
    composable<PokeGuessRoute.Home> {
        HomeScreen(
            onNavigateToMenu = { navController.navigate(PokeGuessRoute.Menu()) },
            onNavigateToHistory = { navController.navigate(PokeGuessRoute.History) }
        )
    }
}

private fun NavGraphBuilder.historyScreen(navController: NavHostController) {
    composable<PokeGuessRoute.History> {
        HistoryScreen(
            onNavigateToScore = { matchId, score, total, withFriends ->
                navController.navigate(
                    PokeGuessRoute.Score(
                        score = score,
                        total = total,
                        withFriends = withFriends,
                        matchId = matchId
                    )
                )
            },
            onNavigateToBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.menuScreen(navController: NavHostController) {
    composable<PokeGuessRoute.Menu> {
        MenuScreen(
            onNavigateToGame = { settings ->
                navController.navigate(PokeGuessRoute.Game(settings)) {
                    popUpTo<PokeGuessRoute.Menu> { inclusive = false }
                }
            },
            onNavigateToPokemons = { generation ->
                navController.navigate(PokeGuessRoute.Pokemons(generation.name)) {
                    popUpTo<PokeGuessRoute.Menu> { inclusive = false }
                }
            },
            onNavigateToBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.gameScreen(navController: NavHostController) {
    composable<PokeGuessRoute.Game>(typeMap = NavTypeUtils.typeMapOf<GameSettings>()) {
        GameScreen(
            onGameOver = { score, total, withFriends ->
                navController.navigate(
                    PokeGuessRoute.Score(
                        score = score,
                        total = total,
                        withFriends = withFriends
                    )
                ) { popUpTo<PokeGuessRoute.Game> { inclusive = true } }
            },
            onNavigateToBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.scoreScreen(navController: NavHostController) {
    composable<PokeGuessRoute.Score> {
        ScoreScreen(
            onPlayAgain = { withFriends ->
                navController.navigate(
                    PokeGuessRoute.Menu(withFriends = withFriends)
                ) { popUpTo<PokeGuessRoute.Menu> { inclusive = true } }
            },
            onBackToHome = {
                navController.navigate(PokeGuessRoute.Home) {
                    popUpTo<PokeGuessRoute.Home> { inclusive = false }
                }
            },
            onNavigateToBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.pokemonScreen(navController: NavHostController) {
    composable<PokeGuessRoute.Pokemons> {
        PokemonScreen(modifier = Modifier, onNavigateToBack = { navController.popBackStack() })
    }
}
