package com.superapp.feature.movies.domain.repository

import com.superapp.feature.movies.data.model.DiscoverMovie
import retrofit2.Response


interface MovieListRepository {
    suspend fun getDiscoverMovies(): Response<DiscoverMovie>
}