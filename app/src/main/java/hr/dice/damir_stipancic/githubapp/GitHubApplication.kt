package hr.dice.damir_stipancic.githubapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import hr.dice.damir_stipancic.githubapp.di.databaseModule
import hr.dice.damir_stipancic.githubapp.di.networkModule
import hr.dice.damir_stipancic.githubapp.di.repositoryModule
import hr.dice.damir_stipancic.githubapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GitHubApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GitHubApplication)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
                networkModule
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .logger(DebugLogger())
            .build()
    }
}
