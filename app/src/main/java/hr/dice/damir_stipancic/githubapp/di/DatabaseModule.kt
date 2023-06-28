package hr.dice.damir_stipancic.githubapp.di

import androidx.room.Room
import hr.dice.damir_stipancic.githubapp.data.database.GitHubDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            GitHubDatabase::class.java,
            "github_search_entries_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<GitHubDatabase>().getRecentGithubRepoSearchDao()
    }
}
