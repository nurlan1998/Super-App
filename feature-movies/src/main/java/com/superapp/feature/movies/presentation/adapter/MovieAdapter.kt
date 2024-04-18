package com.superapp.feature.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.superapp.feature.movies.databinding.ListItemBinding
import com.superapp.feature.movies.domain.model.MoviePoster

internal class MovieAdapter(private val openMovieCallback: (String) -> Unit) :
    ListAdapter<MoviePoster, MovieAdapter.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), openMovieCallback)
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviePoster, openMovieCallback: (String) -> Unit) {
            itemView.setOnClickListener {
                openMovieCallback(movie.title)
            }
            binding.itemMovieText.apply {
                text = movie.title
            }
            binding.itemMovieImage.apply {
                load(movie.posterImageUrl)
                contentDescription = movie.title
                tag = movie.posterImageUrl
            }
        }
    }
}

internal class MovieDiffCallback : DiffUtil.ItemCallback<MoviePoster>() {

    override fun areItemsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
        return oldItem == newItem
    }
}