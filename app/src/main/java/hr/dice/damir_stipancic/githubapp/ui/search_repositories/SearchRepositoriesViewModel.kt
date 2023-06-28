package hr.dice.damir_stipancic.githubapp.ui.search_repositories

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.githubapp.repository.RecentGithubRepoSearchRepository
import java.lang.reflect.Modifier.PRIVATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoriesViewModel(
    private val recentGithubRepoSearchRepository: RecentGithubRepoSearchRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery = _searchQuery.asStateFlow()

    private val _isQueryError = mutableStateOf(false)
    val isQueryError = _isQueryError

    val searchRepositoriesUiState =
        _searchQuery.flatMapLatest { query ->
            recentGithubRepoSearchRepository.getRecentSearchQueries(query.text)
                .map { queries ->
                    SearchRepositoriesUiState(recentSearchQueries = queries)
                }
                .flowOn(Dispatchers.Default)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = SearchRepositoriesUiState.EMPTY
        )

    @VisibleForTesting(otherwise = PRIVATE)
    fun saveRecentSearchQuery(query: String) {
        viewModelScope.launch {
            recentGithubRepoSearchRepository.saveRecentSearchQuery(query)
        }
    }

    fun onValueChanged(value: TextFieldValue) {
        _searchQuery.value = value
        _isQueryError.value = false
    }

    fun onSearchClicked(query: String) {
        val searchInput = query.replace("\\s+".toRegex(), " ").trim()
        if (searchInput.length > 1) {
            saveRecentSearchQuery(searchInput)
        } else {
            _isQueryError.value = true
        }
    }
}
