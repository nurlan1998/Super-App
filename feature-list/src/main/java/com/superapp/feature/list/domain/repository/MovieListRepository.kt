package com.superapp.feature.list.domain.repository

import com.superapp.feature.list.data.model.DiscoverMovie
import retrofit2.Response


interface MovieListRepository {
    suspend fun getDiscoverMovies(): Response<DiscoverMovie>
}