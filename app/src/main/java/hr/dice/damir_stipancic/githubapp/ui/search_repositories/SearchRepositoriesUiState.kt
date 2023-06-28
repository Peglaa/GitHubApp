package hr.dice.damir_stipancic.githubapp.ui.search_repositories

import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearch

data class SearchRepositoriesUiState(
    val recentSearchQueries: List<RecentGithubRepoSearch>
) {
    companion object {
        val EMPTY: SearchRepositoriesUiState = SearchRepositoriesUiState(
            recentSearchQueries = emptyList()
        )
    }
}
