package hr.dice.damir_stipancic.githubapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.damir_stipancic.githubapp.ui.destinations.SearchRepositoriesScreenDestination
import hr.dice.damir_stipancic.githubapp.ui.theme.GitHubAppTheme
import org.koin.androidx.compose.koinViewModel

/**
 * An entire screen component displaying [HomeContent].
 */
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val homeViewModel = koinViewModel<HomeScreenViewModel>()
    HomeContent(
        onPlaceholderClicked = { navigator.navigate(SearchRepositoriesScreenDestination()) },
        onClearConfirmClicked = { homeViewModel.deleteRecentGithubRepoQueries() }
    )
}

/**
 * Content composable for [HomeScreen]. Contains a [Scaffold] with a [TopBar], [GitHubLogo] and
 * [SearchRepositoriesPlaceholder]. Also handles the logic for displaying [SearchEntriesAlertDialog]
 * and handles the viewModel.
 *
 * @param [onClearConfirmClicked] a callback when the user clicks the confirmation button on the
 * [SearchEntriesAlertDialog]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    onPlaceholderClicked: () -> Unit,
    onClearConfirmClicked: () -> Unit
) {
    var showAlert by remember { mutableStateOf(false) }
    if (showAlert) {
        SearchEntriesAlertDialog(
            onDismiss = { showAlert = false },
            onClearConfirmClicked = onClearConfirmClicked
        )
    }
    Scaffold(
        topBar = {
            TopBar(
                onClearMenuItemClicked = { showAlert = true }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GitHubLogo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )

            SearchRepositoriesPlaceholder(
                onClick = onPlaceholderClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    GitHubAppTheme {
        HomeContent(
            onPlaceholderClicked = {},
            onClearConfirmClicked = {}
        )
    }
}
