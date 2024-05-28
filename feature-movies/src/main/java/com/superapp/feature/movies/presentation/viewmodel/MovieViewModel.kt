package com.superapp.feature.movies.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superapp.core.network.models.NetworkResponse
import com.superapp.feature.movies.domain.DiscoverMoviesUseCase
import com.superapp.feature.movies.domain.model.CollectionEntity
import com.superapp.feature.movies.domain.model.MoviePoster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(
    private val useCase: DiscoverMoviesUseCase
) : ViewModel() {
    val moviesLiveData: MutableLiveData<List<CollectionEntity>> = MutableLiveData()
    val errorMessage = MutableLiveData<String>()

    init {
        getMoviesList()
    }

    fun getMoviesList() {
        viewModelScope.launch {
            try {
                val result = useCase.invoke()
                when (result.isSuccess) {
                    true -> {
                        moviesLiveData.value = result.data
                    }

                    false -> {
                        errorMessage.value = result.error
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
                Log.d("isSuccess", e.message.toString())
            }
        }
    }
}