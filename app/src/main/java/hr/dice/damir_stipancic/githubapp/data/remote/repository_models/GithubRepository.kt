package hr.dice.damir_stipancic.githubapp.data.remote.repository_models

import android.icu.text.CompactDecimalFormat
import android.os.Parcelable
import java.util.Locale
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_REPOSITORY_ID = "id"
private const val SER_REPOSITORY_NAME = "name"
private const val SER_REPOSITORY_OWNER = "owner"
private const val SER_REPOSITORY_DESCRIPTION = "description"
private const val SER_REPOSITORY_STARS_COUNT = "stargazers_count"
private const val SER_REPOSITORY_OPEN_ISSUES = "open_issues_count"
private const val SER_REPOSITORY_PRIVATE = "private"
private const val SER_REPOSITORY_WATCHERS = "watchers_count"

@Parcelize
@Serializable
data class GithubRepository(
    @SerialName(SER_REPOSITORY_ID)
    val id: Int,

    @SerialName(SER_REPOSITORY_NAME)
    val name: String,

    @SerialName(SER_REPOSITORY_OWNER)
    val repositoryOwner: RepositoryOwner,

    @SerialName(SER_REPOSITORY_DESCRIPTION)
    val description: String?,

    @SerialName(SER_REPOSITORY_WATCHERS)
    val watchersCount: Int,

    @SerialName(SER_REPOSITORY_STARS_COUNT)
    val starsCount: Int,

    @SerialName(SER_REPOSITORY_PRIVATE)
    val isPrivate: Boolean,

    @SerialName(SER_REPOSITORY_OPEN_ISSUES)
    val openIssues: Int
) : Parcelable {
    @IgnoredOnParcel
    val formattedStarsCount: String = CompactDecimalFormat.getInstance(
        Locale.getDefault(),
        CompactDecimalFormat.CompactStyle.SHORT
    ).format(starsCount)
}
