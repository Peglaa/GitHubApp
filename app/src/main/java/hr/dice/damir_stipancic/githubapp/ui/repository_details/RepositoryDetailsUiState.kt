package hr.dice.damir_stipancic.githubapp.ui.repository_details

import hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item.Detail

data class RepositoryDetailsUiState(
    val title: String,
    val owner: String,
    val profileUrl: String,
    val ownerRepositoriesUrl: String,
    val isPrivate: Boolean,
    val details: List<Detail>
) {
    companion object {
        val EMPTY = RepositoryDetailsUiState(
            title = "",
            owner = "",
            profileUrl = "",
            ownerRepositoriesUrl = "",
            isPrivate = false,
            details = emptyList()
        )
    }
}
