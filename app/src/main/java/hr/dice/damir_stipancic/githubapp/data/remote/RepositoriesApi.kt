package hr.dice.damir_stipancic.githubapp.data.remote

import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoriesApi {

    @GET("repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars"
    ): Response<GithubRepositories>
}
