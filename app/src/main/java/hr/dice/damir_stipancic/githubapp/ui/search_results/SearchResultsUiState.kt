package hr.dice.damir_stipancic.githubapp.ui.search_results

import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepositories

sealed class SearchResultsUiState {
    object Loading : SearchResultsUiState()
    data class Success(val githubRepositories: GithubRepositories) : SearchResultsUiState()
    data class Error(
        val code: Int?,
        val message: String?,
        val isInternetException: Boolean
    ) : SearchResultsUiState()
}
