package com.superapp.feature.list.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurlan.core.network.di.NetworkResponse
import com.superapp.feature.list.domain.DiscoverMoviesUseCase
import com.superapp.feature.list.domain.model.MoviePoster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(
    private val useCase: DiscoverMoviesUseCase
) : ViewModel() {
    val moviesLiveData: MutableLiveData<NetworkResponse<List<MoviePoster>>> = MutableLiveData()
    val errorMessage = MutableLiveData<String>()

    init {
        getMoviesList()
    }

    fun getMoviesList() {
        viewModelScope.launch {
            try {
                val result = useCase.invoke()
                when(result.isSuccess) {
                    true -> {
                        moviesLiveData.value = result
                    }
                    false -> {
                        errorMessage.value = result.error
                    }
                }
            }catch (e: Exception) {
                errorMessage.value = e.message
                Log.d("isSuccess",e.message.toString())
            }
        }
    }
}