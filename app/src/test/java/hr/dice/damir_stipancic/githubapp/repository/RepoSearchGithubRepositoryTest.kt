package hr.dice.damir_stipancic.githubapp.repository

import hr.dice.damir_stipancic.githubapp.data.remote.RepositoriesApi
import hr.dice.damir_stipancic.githubapp.data.remote.Resource
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepositories
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.unmockkAll
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RepoSearchGithubRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var repositoriesApi: RepositoriesApi
    private lateinit var repoSearchRepository: RepoSearchRepository

    @MockK(relaxed = true)
    private lateinit var githubRepositories: GithubRepositories

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repoSearchRepository = spyk(RepoSearchRepository(repositoriesApi))
    }

    @Test
    fun `getRepositories returns success`() = runTest {
        coEvery { repositoriesApi.getRepositories("query") } returns
            Response.success(githubRepositories)

        val expectedState = Resource.Success(githubRepositories)
        val responseState = repoSearchRepository.getRepositories("query")
        coVerify(exactly = 1) { repoSearchRepository.getRepositories("query") }
        assertEquals(expectedState, responseState)
    }

    @Test
    fun `getRepositories when bad response returns error`() = runTest {
        coEvery { repositoriesApi.getRepositories("query") } returns
            Response.error(
                404,
                "{}".toResponseBody()
            )

        val expectedState = Resource.Error<GithubRepositories>(
            code = 404,
            message = "Response.error()",
            isInternetException = false
        )
        val responseState = repoSearchRepository.getRepositories("query")
        coVerify(exactly = 1) { repoSearchRepository.getRepositories("query") }
        assertEquals(expectedState, responseState)
    }

    @Test
    fun `getRepositories when IOException returns error`() = runTest {
        coEvery { repositoriesApi.getRepositories("query") } throws IOException()

        val responseState = repoSearchRepository.getRepositories("query")

        val expectedState = Resource.Error<GithubRepositories>(
            code = null,
            message = null,
            isInternetException = true
        )

        coVerify(exactly = 1) { repoSearchRepository.getRepositories("query") }
        assertEquals(expectedState, responseState)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
