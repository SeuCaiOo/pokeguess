package br.com.seucaio.pokeguess

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.seucaio.pokeguess.core.designsystem.ui.component.PokeGuessTopAppBar
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBackButton =
        navBackStackEntry != null && navController.previousBackStackEntry != null

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            PokeGuessTopAppBar(
                onBackButtonClick = if (!showBackButton) {
                    null
                } else {
                    { navController.popBackStack() }
                }
            )
        },
    ) { innerPadding ->
        PokeGuessNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokeGuessAppPreview() {
    PokeGuessTheme {
        PokeGuessApp()
    }
}
