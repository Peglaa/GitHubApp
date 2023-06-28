package hr.dice.damir_stipancic.githubapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hr.dice.damir_stipancic.githubapp.data.remote.RepositoriesApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.github.com/search/"

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {
    single {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).build()
    }

    single { "application/json".toMediaType() }

    single { Json { ignoreUnknownKeys = true } }

    single { get<Json>().asConverterFactory(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single { get<Retrofit>().create(RepositoriesApi::class.java) }
}
