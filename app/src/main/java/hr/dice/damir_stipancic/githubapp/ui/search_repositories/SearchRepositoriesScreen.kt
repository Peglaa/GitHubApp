package hr.dice.damir_stipancic.githubapp.ui.search_repositories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearch
import hr.dice.damir_stipancic.githubapp.ui.destinations.SearchResultsScreenDestination
import hr.dice.damir_stipancic.githubapp.ui.theme.GitHubAppTheme
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchHintColor
import org.koin.androidx.compose.koinViewModel

/**
 * An entire screen component that displays [SearchRepositoriesContent].
 */
@Destination
@Composable
fun SearchRepositoriesScreen(
    navigator: DestinationsNavigator
) {
    val searchRepositoriesViewModel = koinViewModel<SearchRepositoriesViewModel>()
    val searchRepositoriesUiState: SearchRepositoriesUiState
        by searchRepositoriesViewModel.searchRepositoriesUiState.collectAsStateWithLifecycle()
    val searchQuery = searchRepositoriesViewModel.searchQuery.collectAsStateWithLifecycle()
    val isQueryError by searchRepositoriesViewModel.isQueryError

    SearchRepositoriesContent(
        viewState = searchRepositoriesUiState,
        value = searchQuery.value,
        onSearchClicked = {
            searchRepositoriesViewModel.onSearchClicked(searchQuery.value.text)
            if (!isQueryError) {
                navigator.navigate(SearchResultsScreenDestination(it))
            }
        },
        isQueryError = isQueryError,
        onValueChanged = { searchRepositoriesViewModel.onValueChanged(it) },
        onRecentItemClicked = { navigator.navigate(SearchResultsScreenDestination(it)) },
        onBackArrowClicked = { navigator.navigateUp() }
    )
}

/**
 * Content composable for [SearchRepositoriesScreen]. Contains a [Scaffold] with a top bar and a list
 * of recent search entries or a placeholder text if no search entries are saved.
 *
 * @param [modifier] a [Modifier] component for this composable.
 * @param [viewState] state of the composable
 * @param [onSearchClicked] a callback that triggers when the user clicks the search keyboard button.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchRepositoriesContent(
    modifier: Modifier = Modifier,
    viewState: SearchRepositoriesUiState,
    value: TextFieldValue,
    isQueryError: Boolean,
    onSearchClicked: (String) -> Unit,
    onValueChanged: (TextFieldValue) -> Unit,
    onRecentItemClicked: (String) -> Unit,
    onBackArrowClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppSearchBar(
                onSearchClicked = onSearchClicked,
                value = value,
                onValueChange = { onValueChanged(it) },
                onBackArrowClicked = onBackArrowClicked,
                isError = isQueryError
            )
        }
    ) { paddingValues ->
        if (viewState.recentSearchQueries.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 30.dp, vertical = 16.dp),
                text = stringResource(id = R.string.empty_recent_search_list),
                fontWeight = FontWeight.Bold,
                color = SearchHintColor
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                items(
                    items = viewState.recentSearchQueries,
                    key = { entry -> entry.name }
                ) { entry ->
                    RecentSearchEntryItem(
                        name = entry.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                        onItemClicked = onRecentItemClicked
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchRepositoriesContentEmpty() {
    GitHubAppTheme {
        SearchRepositoriesContent(
            viewState = SearchRepositoriesUiState.EMPTY,
            onSearchClicked = {},
            onValueChanged = {},
            onRecentItemClicked = {},
            onBackArrowClicked = {},
            isQueryError = false,
            value = TextFieldValue("")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchRepositoriesContent() {
    GitHubAppTheme {
        SearchRepositoriesContent(
            viewState = SearchRepositoriesUiState(
                recentSearchQueries = listOf(
                    RecentGithubRepoSearch("Preview1"),
                    RecentGithubRepoSearch("Preview2"),
                    RecentGithubRepoSearch("Preview3"),
                    RecentGithubRepoSearch("Preview4"),
                    RecentGithubRepoSearch("Preview5")

                )
            ),
            onSearchClicked = {},
            onValueChanged = {},
            onRecentItemClicked = {},
            onBackArrowClicked = {},
            isQueryError = false,
            value = TextFieldValue("Preview")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchRepositoriesContentError() {
    GitHubAppTheme {
        SearchRepositoriesContent(
            viewState = SearchRepositoriesUiState.EMPTY,
            onSearchClicked = {},
            onValueChanged = {},
            onRecentItemClicked = {},
            onBackArrowClicked = {},
            isQueryError = true,
            value = TextFieldValue("")
        )
    }
}
