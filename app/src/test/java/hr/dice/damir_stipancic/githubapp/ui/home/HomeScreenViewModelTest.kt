package hr.dice.damir_stipancic.githubapp.ui.home

import hr.dice.damir_stipancic.githubapp.MainDispatcherRule
import hr.dice.damir_stipancic.githubapp.repository.RecentGithubRepoSearchRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var repository: RecentGithubRepoSearchRepository
    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeScreenViewModel(repository)
    }

    @Test
    fun `deleteRecentGithubRepoSearches success`() = runTest {
        viewModel.deleteRecentGithubRepoQueries()
        verify(exactly = 1) { viewModel.deleteRecentGithubRepoQueries() }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
