package hr.dice.damir_stipancic.githubapp.ui.search_results

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.githubapp.data.remote.Resource
import hr.dice.damir_stipancic.githubapp.repository.RepoSearchRepository
import java.lang.reflect.Modifier.PRIVATE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchResultsViewModel(
    private val query: String,
    private val repository: RepoSearchRepository
) : ViewModel() {

    private val _searchResultsUiState =
        MutableStateFlow<SearchResultsUiState>(SearchResultsUiState.Loading)
    val searchResultsUiState = _searchResultsUiState.asStateFlow()

    init {
        getRepositories(query)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getRepositories(query: String) {
        viewModelScope.launch {
            repository.getRepositories(query).let {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null) {
                            _searchResultsUiState.value =
                                SearchResultsUiState.Success(githubRepositories = it.data)
                        }
                    }
                    is Resource.Error ->
                        _searchResultsUiState.value =
                            SearchResultsUiState.Error(
                                code = it.code,
                                message = it.message,
                                isInternetException = it.isInternetException
                            )
                }
            }
        }
    }

    fun onTryAgainClicked() {
        _searchResultsUiState.value = SearchResultsUiState.Loading
        getRepositories(query = query)
    }
}
