package hr.dice.damir_stipancic.githubapp.data.remote.repository_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_REPOSITORIES_TOTAL_COUNT = "total_count"
private const val SER_REPOSITORIES_ITEMS = "items"

@Serializable
data class GithubRepositories(
    @SerialName(SER_REPOSITORIES_TOTAL_COUNT)
    val totalCount: Int,

    @SerialName(SER_REPOSITORIES_ITEMS)
    val repositoriesList: List<GithubRepository>
)
