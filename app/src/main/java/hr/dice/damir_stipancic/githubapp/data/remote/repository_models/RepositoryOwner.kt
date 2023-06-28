package hr.dice.damir_stipancic.githubapp.data.remote.repository_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

private const val SER_REPOSITORY_OWNER_NAME = "login"
private const val SER_REPOSITORY_OWNER_AVATAR_URL = "avatar_url"
private const val SER_REPOSITORY_OWNER_PROFILE_URL = "html_url"
private const val SER_REPOSITORY_OWNER_TYPE = "type"

@Serializable
@Parcelize
data class RepositoryOwner(
    @SerialName(SER_REPOSITORY_OWNER_NAME)
    val name: String,

    @SerialName(SER_REPOSITORY_OWNER_AVATAR_URL)
    val avatarUrl: String,

    @SerialName(SER_REPOSITORY_OWNER_PROFILE_URL)
    val profileUrl: String,

    @SerialName(SER_REPOSITORY_OWNER_TYPE)
    val type: String,

    @Transient
    val repositoriesUrl: String =
        when (type) {
            "Organization" ->
                "https://github.com/orgs/$name/repositories"
            else ->
                "https://github.com/$name?tab=repositories"
        }
) : Parcelable
