package com.superapp.feature.movies.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superapp.feature.movies.databinding.FragmentMoviesListBinding
import com.superapp.feature.movies.domain.navigation.MovieListNavigation
import com.superapp.feature.movies.presentation.adapter.MovieAdapter
import com.superapp.feature.movies.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var navigation: MovieListNavigation

    private val adapter by lazy { MovieAdapter(navigation::openMovie) }
    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val concatAdapter = ConcatAdapter()
        concatAdapter.addAdapter(adapter)

        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.movieRecycler.run {
            layoutManager = linearLayoutManager
            adapter = concatAdapter
            enforceSingleScrollDirection()
        }
        binding.movieProgress.visibility = View.VISIBLE

        viewModel.moviesLiveData.observe(requireActivity()) {
            adapter.setData(it)
            binding.movieProgress.visibility = View.INVISIBLE
        }

        viewModel.errorMessage.observe(requireActivity()) {
            binding.movieProgress.visibility = View.INVISIBLE
            binding.movieError.visibility = View.VISIBLE
            binding.movieError.text = it
        }
    }

    private fun RecyclerView.enforceSingleScrollDirection() {
        val enforcer = SingleScrollDirectionEnforcer()
        addOnItemTouchListener(enforcer)
        addOnScrollListener(enforcer)
    }

    private class SingleScrollDirectionEnforcer : RecyclerView.OnScrollListener(), RecyclerView.OnItemTouchListener {

        private var scrollState = RecyclerView.SCROLL_STATE_IDLE
        private var scrollPointerId = -1
        private var initialTouchX = 0
        private var initialTouchY = 0
        private var dx = 0
        private var dy = 0

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            when (e.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    scrollPointerId = e.getPointerId(0)
                    initialTouchX = (e.x + 0.5f).toInt()
                    initialTouchY = (e.y + 0.5f).toInt()
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    val actionIndex = e.actionIndex
                    scrollPointerId = e.getPointerId(actionIndex)
                    initialTouchX = (e.getX(actionIndex) + 0.5f).toInt()
                    initialTouchY = (e.getY(actionIndex) + 0.5f).toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val index = e.findPointerIndex(scrollPointerId)
                    if (index >= 0 && scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
                        val x = (e.getX(index) + 0.5f).toInt()
                        val y = (e.getY(index) + 0.5f).toInt()
                        dx = x - initialTouchX
                        dy = y - initialTouchY
                    }
                }
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            val oldState = scrollState
            scrollState = newState
            if (oldState == RecyclerView.SCROLL_STATE_IDLE && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                recyclerView.layoutManager?.let { layoutManager ->
                    val canScrollHorizontally = layoutManager.canScrollHorizontally()
                    val canScrollVertically = layoutManager.canScrollVertically()
                    if (canScrollHorizontally != canScrollVertically) {
                        if ((canScrollHorizontally && abs(dy) > abs(dx))
                            || (canScrollVertically && abs(dx) > abs(dy))) {
                            recyclerView.stopScroll()
                        }
                    }
                }
            }
        }
    }
}