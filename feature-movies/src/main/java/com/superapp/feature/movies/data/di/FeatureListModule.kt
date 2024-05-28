package com.superapp.feature.movies.data.di

import android.app.Application
import android.content.Context
import com.superapp.core.database.models.AppDatabase
import com.superapp.core.database.models.MovieDao
import com.superapp.feature.movies.androidmanagers.NetManager
import com.superapp.feature.movies.data.network.ApiClient
import com.superapp.feature.movies.data.repository.MovieListRepositoryImpl
import com.superapp.feature.movies.data.repository.MovieLocalDataSource
import com.superapp.feature.movies.data.repository.MovieRemoteDataSource
import com.superapp.feature.movies.domain.DiscoverMoviesUseCase
import com.superapp.feature.movies.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureListModule {
    @Provides
    internal fun providesService(retrofit: Retrofit): ApiClient =
        retrofit.create(ApiClient::class.java)

    @Provides
    @Singleton
    internal fun providePopularRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        netManager: NetManager,
    ): MovieListRepository {
        return MovieListRepositoryImpl(movieRemoteDataSource, movieLocalDataSource,netManager)
    }

    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

//    @Provides
//    @Singleton
//    internal fun provideMovieUseCse(movieListRepository: MovieListRepository): DiscoverMoviesUseCase {
//        return DiscoverMoviesUseCase(movieListRepository)
//    }
//    @Module
//    @InstallIn(SingletonComponent::class)
//    internal interface BindsModule {
//
//        @Binds
//        @Singleton
//        fun bindMovieRepository(impl: MovieListRepositoryImpl): MovieListRepository
//    }
}