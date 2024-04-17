package com.superapp.feature.list.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.superapp.feature.list.databinding.FragmentMovieListBinding
import com.superapp.feature.list.domain.navigation.MovieListNavigation
import com.superapp.feature.list.presentation.adapter.MovieAdapter
import com.superapp.feature.list.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var navigation: MovieListNavigation

    private val adapter by lazy { MovieAdapter(navigation::openMovie) }
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieRecycler.adapter = adapter
        binding.movieProgress.visibility = View.VISIBLE

        viewModel.moviesLiveData.observe(requireActivity()) {
            adapter.submitList(it.data)
            binding.movieProgress.visibility = View.INVISIBLE
        }

        viewModel.errorMessage.observe(requireActivity()) {
            binding.movieProgress.visibility = View.INVISIBLE
            binding.movieError.visibility = View.VISIBLE
            binding.movieError.text = it
        }
    }
}