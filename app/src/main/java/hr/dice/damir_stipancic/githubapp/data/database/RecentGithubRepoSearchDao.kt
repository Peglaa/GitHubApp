package hr.dice.damir_stipancic.githubapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentGithubRepoSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentGithubRepoSearch(recentGithubRepoSearch: RecentGithubRepoSearch)

    @Query("DELETE FROM $TN_SEARCH_QUERY")
    suspend fun deleteAllQueries()

    @Query(
        """
        SELECT * FROM $TN_SEARCH_QUERY
        WHERE $COL_SEARCH_QUERY_NAME LIKE '%' || :filter || '%'
        ORDER by $COL_SEARCH_QUERY_NAME
        """
    )
    fun getRecentSearchEntries(filter: String): Flow<List<RecentGithubRepoSearch>>
}
