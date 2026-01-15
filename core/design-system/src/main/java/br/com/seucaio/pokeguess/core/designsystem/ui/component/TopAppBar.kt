package br.com.seucaio.pokeguess.core.designsystem.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.seucaio.pokeguess.core.designsystem.R
import br.com.seucaio.pokeguess.core.designsystem.ui.theme.PokeGuessTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeGuessTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackButtonClick: (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = { Text(title) },
        navigationIcon = {
            onBackButtonClick?.let { onBackButtonClick ->
                IconButton(onClick = { onBackButtonClick.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PokeGuessTopAppBarPreview() {
    PokeGuessTheme {
        PokeGuessTopAppBar(onBackButtonClick = {})
    }
}
