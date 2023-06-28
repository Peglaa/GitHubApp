package hr.dice.damir_stipancic.githubapp.ui.repository_details

import app.cash.turbine.test
import hr.dice.damir_stipancic.githubapp.MainDispatcherRule
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepository
import hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item.Detail
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class RepositoryDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var repository: GithubRepository

    @MockK(relaxed = true)
    private lateinit var details: List<Detail>
    private lateinit var viewModel: RepositoryDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = RepositoryDetailsViewModel(repository)
    }

    @Test
    fun `repositoryDetailsUiState shows initial empty state`() = runTest {
        val expectedState = RepositoryDetailsUiState.EMPTY
        launch {
            viewModel.repositoryDetailsUiState.test {
                val actualState = awaitItem()
                Assert.assertEquals(expectedState, actualState)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @Test
    fun `repositoryDetailsUiState shows success state`() = runTest {
        val expectedState = RepositoryDetailsUiState(
            title = repository.name,
            owner = repository.repositoryOwner.name,
            profileUrl = repository.repositoryOwner.profileUrl,
            ownerRepositoriesUrl = repository.repositoryOwner.repositoriesUrl,
            isPrivate = repository.isPrivate,
            details = details
        )
        launch {
            viewModel.repositoryDetailsUiState.drop(1).test {
                val actualState = awaitItem()
                Assert.assertEquals(expectedState, actualState)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
