package hr.dice.damir_stipancic.githubapp.ui.search_repositories

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import hr.dice.damir_stipancic.githubapp.MainDispatcherRule
import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearch
import hr.dice.damir_stipancic.githubapp.repository.RecentGithubRepoSearchRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class SearchGithubRepositoriesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var repository: RecentGithubRepoSearchRepository
    private lateinit var viewModel: SearchRepositoriesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(SearchRepositoriesViewModel(repository))
    }

    @Test
    fun `searchRepositoriesViewState shows initial empty state`() = runTest {
        val expectedState = SearchRepositoriesUiState.EMPTY
        launch {
            viewModel.searchRepositoriesUiState.test {
                val initialState = awaitItem()
                assertEquals(expectedState, initialState)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @Test
    fun `searchRepositoriesViewState shows non-filtered state`() = runTest {
        val recentSearchEntries = listOf(
            RecentGithubRepoSearch(name = "Test1"),
            RecentGithubRepoSearch(name = "Test2"),
            RecentGithubRepoSearch(name = "Test3")
        )

        every { viewModel.searchQuery } returns MutableStateFlow(TextFieldValue("")).asStateFlow()
        val expectedState = SearchRepositoriesUiState(
            recentSearchQueries = recentSearchEntries
        )

        launch {
            viewModel.searchRepositoriesUiState.drop(1).test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @Test
    fun `searchRepositoriesViewState shows filtered state`() = runTest {
        val recentSearchEntries = listOf(
            RecentGithubRepoSearch(name = "Test1"),
            RecentGithubRepoSearch(name = "Test2"),
            RecentGithubRepoSearch(name = "Test3"),
            RecentGithubRepoSearch(name = "Android")
        )
        val searchQuery = "a"
        val filteredList = recentSearchEntries.filter { it.name.contains(searchQuery) }
        every { viewModel.searchQuery } returns
            MutableStateFlow(TextFieldValue(searchQuery)).asStateFlow()
        val expectedState = SearchRepositoriesUiState(
            recentSearchQueries = filteredList
        )

        launch {
            viewModel.searchRepositoriesUiState.drop(1).test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
    }

    @Test
    fun `saveRecentSearchQuery success`() = runTest {
        viewModel.saveRecentSearchQuery(query = "Test")
        verify(exactly = 1) { viewModel.saveRecentSearchQuery(query = "Test") }
    }

    @Test
    fun `onValueChanged success`() = runTest {
        val testQuery = "test"
        viewModel.onValueChanged(TextFieldValue(testQuery))
        assertEquals(TextFieldValue(testQuery), viewModel.searchQuery.value)
        assertEquals(false, viewModel.isQueryError.value)
        verify(exactly = 1) { viewModel.onValueChanged(TextFieldValue(testQuery)) }
    }

    @Test
    fun `onSearchClicked success`() = runTest {
        val testQuery = "test"
        viewModel.onSearchClicked(query = testQuery)
        assertEquals(false, viewModel.isQueryError.value)
        verify(exactly = 1) { viewModel.onSearchClicked(query = testQuery) }
        verify(exactly = 1) { viewModel.saveRecentSearchQuery(query = testQuery) }
    }

    @Test
    fun `onSearchClicked error`() = runTest {
        val testQuery = "t"
        viewModel.onSearchClicked(query = testQuery)
        assertEquals(true, viewModel.isQueryError.value)
        verify(exactly = 1) { viewModel.onSearchClicked(query = testQuery) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
