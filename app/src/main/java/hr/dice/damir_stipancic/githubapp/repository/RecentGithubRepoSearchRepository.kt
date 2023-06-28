package hr.dice.damir_stipancic.githubapp.repository

import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearch
import hr.dice.damir_stipancic.githubapp.data.database.RecentGithubRepoSearchDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * This class is responsible for handling the interactions with the local database
 *
 * @param [recentGithubRepoSearchDao] a [RecentGithubRepoSearchDao] Room database Dao
 */
class RecentGithubRepoSearchRepository(
    private val recentGithubRepoSearchDao: RecentGithubRepoSearchDao
) {
    /**
     * Deletes all [RecentGithubRepoSearch] items from the database
     */
    suspend fun deleteRecentGithubRepoQueries() {
        withContext(Dispatchers.IO) {
            recentGithubRepoSearchDao.deleteAllQueries()
        }
    }

    /**
     * Fetches [RecentGithubRepoSearch] items from the database based on the
     * passed filter string, sorted alphabetically
     *
     * @param [filter] a [String] filter used to, that's right, filter the database for matching
     * items
     *
     * @return [Flow] of a [List] of [RecentGithubRepoSearch] items
     */
    fun getRecentSearchQueries(filter: String): Flow<List<RecentGithubRepoSearch>> {
        return recentGithubRepoSearchDao.getRecentSearchEntries(filter).flowOn(Dispatchers.IO)
    }

    /**
     * Saves the most recent search query into the local database
     *
     * @param [name] a [String] representing the search query
     */
    suspend fun saveRecentSearchQuery(name: String) {
        withContext(Dispatchers.IO) {
            recentGithubRepoSearchDao.insertRecentGithubRepoSearch(RecentGithubRepoSearch(name))
        }
    }
}
