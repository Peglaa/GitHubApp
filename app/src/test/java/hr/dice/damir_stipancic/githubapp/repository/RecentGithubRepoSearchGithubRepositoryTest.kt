package hr.dice.damir_stipancic.githubapp.repository

import app.cash.turbine.test
import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearch
import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearchDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class RecentGithubRepoSearchGithubRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var recentGithubRepoSearchDao: RecentGithubRepoSearchDao
    private lateinit var repository: RecentGithubRepoSearchRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = spyk(RecentGithubRepoSearchRepository(recentGithubRepoSearchDao))
        every { recentGithubRepoSearchDao.getRecentSearchEntries("") } returns flowOf(
            listOf()
        )
    }

    @Test
    fun `deleteRecentGithubRepoSearches success`() = runTest {
        repository.deleteRecentGithubRepoQueries()
        coVerify(exactly = 1) { repository.deleteRecentGithubRepoQueries() }
    }

    @Test
    fun `saveRecentSearchEntry success`() = runTest {
        repository.saveRecentSearchQuery(name = "Test")
        coVerify(exactly = 1) { repository.saveRecentSearchQuery(name = "Test") }
    }

    @Test
    fun `getRecentSearchEntries success`() = runTest {
        val filter = "Test"
        coEvery {
            recentGithubRepoSearchDao.getRecentSearchEntries(filter = filter)
        } returns flowOf(
            emptyList()
        )
        val recentEntries = repository.getRecentSearchQueries(filter = filter)
        launch {
            recentEntries.test {
                val recentEntriesList = awaitItem()
                assertEquals(emptyList<RecentGithubRepoSearch>(), recentEntriesList)
                cancelAndIgnoreRemainingEvents()
            }
        }.join()
        verify(exactly = 1) { repository.getRecentSearchQueries(filter = filter) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
