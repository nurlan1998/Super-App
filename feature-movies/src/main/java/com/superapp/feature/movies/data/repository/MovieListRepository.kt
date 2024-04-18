package com.superapp.feature.movies.data.repository

import com.superapp.feature.movies.data.network.ApiClient
import com.superapp.feature.movies.domain.repository.MovieListRepository

internal class MovieListRepositoryImpl(private val client: ApiClient) : MovieListRepository {
    override suspend fun getDiscoverMovies() = client.getMovies()
}