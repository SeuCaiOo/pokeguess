package br.com.seucaio.pokeguess.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
                onNavigateToMenuFriends = { navController.navigate(PokeGuessRoute.Menu(true)) },
                onNavigateToHistory = { navController.navigate(PokeGuessRoute.History) }
            )
        }

        composable<PokeGuessRoute.History> {

        }

        composable<PokeGuessRoute.Menu> {
            MenuScreen(
                onNavigateToGame = { generation, timerEnabled, rounds, playerName, withFriends ->
                    navController.navigate(
                        PokeGuessRoute.Game(
                            generation = generation.name,
                            timerEnabled = timerEnabled,
                            rounds = rounds,
                            playerName = playerName,
                            withFriends = withFriends
                        )
                    ) { popUpTo<PokeGuessRoute.Menu> { inclusive = false } }
                }
            )
        }

        composable<PokeGuessRoute.Game> {
            GameScreen(
                onGameOver = { score, total, withFriends ->
                    navController.navigate(
                        PokeGuessRoute.Score(
                            score = score,
                            total = total,
                            withFriends = withFriends
                        )
                    ) { popUpTo<PokeGuessRoute.Game> { inclusive = true } }
                }
            )
        }

        composable<PokeGuessRoute.Score> { backStackEntry ->
            val scoreRoute = backStackEntry.toRoute<PokeGuessRoute.Score>()
            ScoreScreen(
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
