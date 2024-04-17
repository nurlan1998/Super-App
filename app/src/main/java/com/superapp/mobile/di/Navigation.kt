package com.superapp.mobile.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.superapp.feature.list.domain.navigation.MovieListNavigation
import com.superapp.mobile.R
import com.superapp.mobile.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {
    @Provides
    fun navController(activity: FragmentActivity): NavController {
        return NavHostFragment.findNavController(activity.supportFragmentManager.findFragmentById(R.id.navHostFragment)!!)
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieListModule {
    @Binds
    abstract fun movieList(navigator: Navigator): MovieListNavigation
}