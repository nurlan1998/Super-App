package com.superapp.feature.list.data.repository

import com.superapp.feature.list.data.network.ApiClient
import com.superapp.feature.list.domain.repository.MovieListRepository

internal class MovieListRepositoryImpl(private val client: ApiClient) : MovieListRepository {
    override suspend fun getDiscoverMovies() = client.getMovies()
}