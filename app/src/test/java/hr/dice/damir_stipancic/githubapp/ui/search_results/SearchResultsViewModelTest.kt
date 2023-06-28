package hr.dice.damir_stipancic.githubapp.ui.search_results

import app.cash.turbine.test
import hr.dice.damir_stipancic.githubapp.MainDispatcherRule
import hr.dice.damir_stipancic.githubapp.data.remote.Resource
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepositories
import hr.dice.damir_stipancic.githubapp.repository.RepoSearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class SearchResultsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var repository: RepoSearchRepository
    private lateinit var viewModel: SearchResultsViewModel

    @MockK(relaxed = true)
    private lateinit var githubRepositories: GithubRepositories

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            SearchResultsViewModel(
                repository = repository,
                query = "test"
            )
        )
    }

    @Test
    fun `searchResultsUiState shows initial loading state`() = runTest {
        val expectedState = SearchResultsUiState.Loading
        launch {
            viewModel.searchResultsUiState.test {
                val actualState = awaitItem()
                assertEquals(expectedState, actualState)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @Test
    fun `searchResultsUiState shows success state`() = runTest {
        coEvery { repository.getRepositories("test") } returns
            Resource.Success(data = githubRepositories)
        val expectedState = SearchResultsUiState.Success(githubRepositories = githubRepositories)
        launch {
            viewModel.searchResultsUiState.drop(1).test {
                val actualState = awaitItem()
                assertEquals(expectedState, actualState)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @Test
    fun `onTryAgainClicked success`() = runTest {
        viewModel.onTryAgainClicked()
        launch {
            viewModel.searchResultsUiState.test {
                val state = awaitItem()
                assertEquals(SearchResultsUiState.Loading, state)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()

        verify(exactly = 1) { viewModel.getRepositories("test") }
    }

    @Test
    fun `getRepository success`() = runTest {
        coEvery { repository.getRepositories("test") } returns
            Resource.Success(data = githubRepositories)

        viewModel.getRepositories("test")
        val expectedState = SearchResultsUiState.Success(githubRepositories = githubRepositories)
        launch {
            viewModel.searchResultsUiState.test {
                val state = awaitItem()
                assertEquals(expectedState, state)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()

        verify(exactly = 1) { viewModel.getRepositories("test") }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
