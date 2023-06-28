package hr.dice.damir_stipancic.githubapp.di

import hr.dice.damir_stipancic.githubapp.repository.RecentGithubRepoSearchRepository
import hr.dice.damir_stipancic.githubapp.repository.RepoSearchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::RecentGithubRepoSearchRepository)
    singleOf(::RepoSearchRepository)
}
