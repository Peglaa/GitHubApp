package hr.dice.damir_stipancic.githubapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TN_SEARCH_QUERY = "search_queries"
const val COL_SEARCH_QUERY_NAME = "name"

@Entity(tableName = TN_SEARCH_QUERY)
data class RecentGithubRepoSearch(
    @PrimaryKey
    @ColumnInfo(name = COL_SEARCH_QUERY_NAME)
    val name: String
)
