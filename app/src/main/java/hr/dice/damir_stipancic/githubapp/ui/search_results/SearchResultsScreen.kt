package hr.dice.damir_stipancic.githubapp.ui.search_results

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepositories
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepository
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.RepositoryOwner
import hr.dice.damir_stipancic.githubapp.ui.core.ErrorContent
import hr.dice.damir_stipancic.githubapp.ui.core.LoadingContent
import hr.dice.damir_stipancic.githubapp.ui.destinations.RepositoryDetailsScreenDestination
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey
import hr.dice.damir_stipancic.githubapp.ui.theme.VeryLightGray
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * An entire screen component that displays [SearchResultsContent], [LoadingContent] or [ErrorContent]
 * depending on current state.
 *
 * @param [query] a [String] parameter representing the search query that produced the current state
 */
@Destination
@Composable
fun SearchResultsScreen(
    navigator: DestinationsNavigator,
    query: String
) {
    val searchResultsViewModel = koinViewModel<SearchResultsViewModel> { parametersOf(query) }
    val searchResultsUiState: SearchResultsUiState
        by searchResultsViewModel.searchResultsUiState.collectAsStateWithLifecycle()

    when (searchResultsUiState) {
        is SearchResultsUiState.Loading -> LoadingContent()

        is SearchResultsUiState.Success -> SearchResultsContent(
            searchResultsUiState = searchResultsUiState as SearchResultsUiState.Success,
            onItemClicked = {
                navigator.navigate(
                    RepositoryDetailsScreenDestination(it)
                )
            }
        )

        is SearchResultsUiState.Error ->
            ErrorContent(
                isInternetException = (searchResultsUiState as SearchResultsUiState.Error)
                    .isInternetException,
                onTryAgainClicked = { searchResultsViewModel.onTryAgainClicked() }
            )
    }
}

/**
 * Content composable for [SearchResultsScreen]. It contain a [Scaffold] with a [TopAppBar]
 * that displays the total amount of found repositories and a [LazyColumn] that displays a list of
 * [SearchResultItem]s.
 *
 * @param [searchResultsUiState] a [SearchResultsUiState.Success] object that holds search results
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResultsContent(
    searchResultsUiState: SearchResultsUiState.Success,
    onItemClicked: (GithubRepository) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(VeryLightGray),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        stickyHeader {
            Text(
                modifier = Modifier
                    .background(VeryLightGray)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                text = stringResource(
                    R.string.total_count,
                    "%,d".format(searchResultsUiState.githubRepositories.totalCount)
                ),
                color = DarkGrey,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
        items(
            items = searchResultsUiState.githubRepositories.repositoriesList,
            key = { repository -> repository.id }
        ) { repository ->
            SearchResultItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = repository.name,
                repositoryOwner = repository.repositoryOwner.name,
                description = repository.description ?: "",
                starsCount = repository.formattedStarsCount,
                onItemClicked = { onItemClicked(repository) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchResultsContent() {
    SearchResultsContent(
        searchResultsUiState = SearchResultsUiState.Success(
            githubRepositories = GithubRepositories(
                totalCount = 1000,
                repositoriesList = mockRepositoryList()
            )
        ),
        onItemClicked = { }
    )
}

private fun mockRepositoryList(): List<GithubRepository> {
    return (1..5).map {
        GithubRepository(
            id = it,
            name = "Preview name",
            repositoryOwner = RepositoryOwner(
                name = "Preview author",
                avatarUrl = "",
                profileUrl = "",
                repositoriesUrl = "",
                type = "user"
            ),
            description = "Preview description",
            starsCount = 10,
            isPrivate = false,
            openIssues = 1,
            watchersCount = 1
        )
    }
}
