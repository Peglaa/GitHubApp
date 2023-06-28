package hr.dice.damir_stipancic.githubapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.githubapp.repository.RecentGithubRepoSearchRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val recentGithubRepoSearchRepository: RecentGithubRepoSearchRepository
) : ViewModel() {

    fun deleteRecentGithubRepoQueries() {
        viewModelScope.launch {
            recentGithubRepoSearchRepository.deleteRecentGithubRepoQueries()
        }
    }
}
