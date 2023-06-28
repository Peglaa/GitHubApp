package hr.dice.damir_stipancic.githubapp.ui.repository_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepository
import hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item.Detail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    githubRepository: GithubRepository
) : ViewModel() {

    private val _repositoryDetailsUiState = MutableStateFlow(RepositoryDetailsUiState.EMPTY)
    val repositoryDetailsUiState = _repositoryDetailsUiState.asStateFlow()

    init {
        prepareUiData(githubRepository)
    }

    private fun prepareUiData(githubRepository: GithubRepository) {
        viewModelScope.launch(Dispatchers.Default) {
            val privateDrawable =
                if (githubRepository.isPrivate) {
                    R.drawable.lock
                } else {
                    R.drawable.lock_open
                }
            val privateString =
                if (githubRepository.isPrivate) {
                    R.string.private_yes
                } else {
                    R.string.private_no
                }

            val details = listOf(
                Detail.ImageDetail(
                    title = R.string.owner_title,
                    imageUrl = githubRepository.repositoryOwner.avatarUrl,
                    text = githubRepository.repositoryOwner.name
                ),
                Detail.TextDetail(
                    title = R.string.description_title,
                    text = githubRepository.description ?: ""
                ),
                Detail.IconDetail.IconDetailString(
                    stringTitle = R.string.opened_issues_title,
                    stringIconId = R.drawable.issues,
                    value = githubRepository.openIssues.toString()
                ),
                Detail.IconDetail.IconDetailString(
                    stringTitle = R.string.watchers_title,
                    stringIconId = R.drawable.watchers,
                    value = githubRepository.watchersCount.toString()
                ),
                Detail.IconDetail.IconDetailString(
                    stringTitle = R.string.stars_title,
                    stringIconId = R.drawable.star_border,
                    value = githubRepository.starsCount.toString()
                ),
                Detail.IconDetail.IconDetailStringRes(
                    stringResTitle = R.string.private_title,
                    stringResIconId = privateDrawable,
                    value = privateString
                )
            )
            _repositoryDetailsUiState.value =
                RepositoryDetailsUiState(
                    title = githubRepository.name,
                    owner = githubRepository.repositoryOwner.name,
                    profileUrl = githubRepository.repositoryOwner.profileUrl,
                    ownerRepositoriesUrl = githubRepository.repositoryOwner.repositoriesUrl,
                    isPrivate = githubRepository.isPrivate,
                    details = details
                )
        }
    }
}
