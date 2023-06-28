package hr.dice.damir_stipancic.githubapp.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey

/**
 * A simple composable that only shows a [CircularProgressIndicator] as a loading indicator.
 *
 * @param [modifier] a [Modifier] for this component
 */
@Composable
fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = DarkGrey
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingContent() {
    LoadingContent()
}
