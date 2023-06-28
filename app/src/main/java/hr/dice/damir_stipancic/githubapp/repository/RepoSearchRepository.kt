package hr.dice.damir_stipancic.githubapp.repository

import hr.dice.damir_stipancic.githubapp.data.remote.RepositoriesApi
import hr.dice.damir_stipancic.githubapp.data.remote.Resource
import hr.dice.damir_stipancic.githubapp.data.remote.callApi
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepositories

/**
 * This class is responsible for handling the interactions with the network api
 *
 * @param [repositoriesApi] a [RepositoriesApi] object used to perform api calls
 */
class RepoSearchRepository(
    private val repositoriesApi: RepositoriesApi
) {
    /**
     * Fetches all repositories matching a specific query
     *
     * @param [query] a [String] representing a search query
     *
     * @return a [Resource] of type [GithubRepositories]
     */
    suspend fun getRepositories(query: String): Resource<GithubRepositories> {
        return callApi {
            repositoriesApi.getRepositories(query)
        }
    }
}
