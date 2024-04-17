package com.superapp.mobile.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.superapp.feature.list.domain.navigation.MovieListNavigation
import com.superapp.mobile.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Navigator @Inject constructor(
    private val navController: NavController
) : MovieListNavigation {

    override fun openMovie(title: String) {
        val bundle = Bundle()
        bundle.putString("movie_title", title)
        navController.navigate(R.id.detailsFragment, bundle)
    }
}
