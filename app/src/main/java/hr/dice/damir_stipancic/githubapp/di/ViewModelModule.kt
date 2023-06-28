package hr.dice.damir_stipancic.githubapp.di

import hr.dice.damir_stipancic.githubapp.ui.home.HomeScreenViewModel
import hr.dice.damir_stipancic.githubapp.ui.repository_details.RepositoryDetailsViewModel
import hr.dice.damir_stipancic.githubapp.ui.search_repositories.SearchRepositoriesViewModel
import hr.dice.damir_stipancic.githubapp.ui.search_results.SearchResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::SearchRepositoriesViewModel)
    viewModelOf(::SearchResultsViewModel)
    viewModelOf(::RepositoryDetailsViewModel)
}
