package br.com.seucaio.pokeguess.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.features.game.GameScreen
import br.com.seucaio.pokeguess.features.home.HomeScreen
import br.com.seucaio.pokeguess.features.menu.MenuScreen
import br.com.seucaio.pokeguess.features.score.ScoreScreen

@Composable
fun PokeGuessNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PokeGuessRoute.Home
    ) {
        composable<PokeGuessRoute.Home> {
            HomeScreen(
                onNavigateToMenuSolo = { navController.navigate(PokeGuessRoute.Menu(false)) },
                onNavigateToMenuFriends = { navController.navigate(PokeGuessRoute.Menu(true)) }
            )
        }

        composable<PokeGuessRoute.Menu> {
            MenuScreen(
                onNavigateToGame = { generation, timerEnabled ->
                    navController.navigate(PokeGuessRoute.Game(generation.name, timerEnabled)) {
                        popUpTo<PokeGuessRoute.Menu> { inclusive = false }
                    }
                }
            )
        }

        composable<PokeGuessRoute.Game> { backStackEntry ->
            val gameRoute = backStackEntry.toRoute<PokeGuessRoute.Game>()
            val generation = Generation.valueOf(gameRoute.generation)
            GameScreen(
                generation = generation,
                timerEnabled = gameRoute.timerEnabled,
                onGameOver = { score, total ->
                    navController.navigate(
                        PokeGuessRoute.Score(
                            score = score,
                            total = total,
                            withFriends = gameRoute.withFriends
                        )
                    ) { popUpTo<PokeGuessRoute.Game> { inclusive = true } }
                }
            )
        }

        composable<PokeGuessRoute.Score> { backStackEntry ->
            val scoreRoute = backStackEntry.toRoute<PokeGuessRoute.Score>()
            ScoreScreen(
                score = scoreRoute.score,
                total = scoreRoute.total,
                onPlayAgain = {
                    navController.navigate(
                        PokeGuessRoute.Menu(withFriends = scoreRoute.withFriends)
                    ) { popUpTo<PokeGuessRoute.Menu> { inclusive = true } }
                },
                onBackToHome = {
                    navController.navigate(PokeGuessRoute.Home) {
                        popUpTo<PokeGuessRoute.Home> { inclusive = false }
                    }
                }
            )
        }
    }
}
