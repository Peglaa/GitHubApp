package hr.dice.damir_stipancic.githubapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import hr.dice.damir_stipancic.githubapp.R

/**
 * A simple [Image] component used in [HomeScreen].
 *
 * @param [modifier] a [Modifier] for this [Image]
 */
@Composable
fun GitHubLogo(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.github_logo),
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewGitHubLogo() {
    GitHubLogo()
}
