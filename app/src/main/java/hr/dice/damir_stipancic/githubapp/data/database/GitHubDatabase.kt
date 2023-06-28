package hr.dice.damir_stipancic.githubapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentGithubRepoSearch::class], version = 3, exportSchema = false)
abstract class GitHubDatabase : RoomDatabase() {

    abstract fun getRecentGithubRepoSearchDao(): RecentGithubRepoSearchDao
}
