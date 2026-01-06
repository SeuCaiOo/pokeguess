package br.com.seucaio.pokeguess

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.domain.model.Generation
import br.com.seucaio.pokeguess.features.game.GameScreen
import br.com.seucaio.pokeguess.features.menu.MenuScreen
import br.com.seucaio.pokeguess.features.score.ScoreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeGuessTheme {
                PokeGuessApp()
            }
        }
    }
}

@Composable
fun PokeGuessApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") {
                MenuScreen(
                    onNavigateToGame = { generation, timerEnabled ->
                        navController.navigate("game/${generation.name}/$timerEnabled")
                    }
                )
            }

            composable(
                route = "game/{generation}/{timerEnabled}",
                arguments = listOf(
                    navArgument("generation") { type = NavType.StringType },
                    navArgument("timerEnabled") { type = NavType.BoolType }
                )
            ) { backStackEntry ->
                val generationName = backStackEntry.arguments?.getString("generation") ?: Generation.I.name
                val generation = Generation.valueOf(generationName)
                val timerEnabled = backStackEntry.arguments?.getBoolean("timerEnabled") ?: false

                GameScreen(
                    generation = generation,
                    timerEnabled = timerEnabled,
                    onGameOver = { score, total ->
                        navController.navigate("score/$score/$total") {
                            popUpTo("menu") { inclusive = false }
                        }
                    }
                )
            }

            composable(
                route = "score/{score}/{total}",
                arguments = listOf(
                    navArgument("score") { type = NavType.IntType },
                    navArgument("total") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val score = backStackEntry.arguments?.getInt("score") ?: 0
                val total = backStackEntry.arguments?.getInt("total") ?: 0

                ScoreScreen(
                    score = score,
                    total = total,
                    onPlayAgain = {
                        navController.navigate("game") {
                            popUpTo("menu") { inclusive = false }
                        }
                    },
                    onBackToMenu = {
                        navController.navigate("menu") {
                            popUpTo("menu") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokeGuessAppPreview() {
    PokeGuessTheme {
        PokeGuessApp()
    }
}
