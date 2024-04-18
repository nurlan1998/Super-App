package com.superapp.feature.movies.data.di

import com.superapp.feature.movies.data.network.ApiClient
import com.superapp.feature.movies.data.repository.MovieListRepositoryImpl
import com.superapp.feature.movies.domain.repository.MovieListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    internal fun providePopularRepository(client: ApiClient): MovieListRepository {
        return MovieListRepositoryImpl(client)
    }
}