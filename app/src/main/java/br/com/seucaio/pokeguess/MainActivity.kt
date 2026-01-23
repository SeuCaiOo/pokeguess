package br.com.seucaio.pokeguess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme
import br.com.seucaio.pokeguess.navigation.PokeGuessNavGraph

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
fun PokeGuessApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    PokeGuessNavGraph(
        navController = navController,
        modifier = modifier.padding()
    )
}
