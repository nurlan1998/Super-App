package com.superapp.feature.movies.domain.repository

import com.superapp.feature.movies.domain.model.CollectionEntity
import retrofit2.Response


interface MovieListRepository {
    suspend fun getDiscoverMovies(): Response<MutableList<CollectionEntity>>
}