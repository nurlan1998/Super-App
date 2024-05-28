package com.superapp.feature.movies.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.superapp.feature.movies.databinding.HorizontalItemBinding
import com.superapp.feature.movies.domain.model.MoviePoster
import kotlinx.coroutines.Dispatchers

internal class MoviesHorizontalAdapter :
    ListAdapter<MoviePoster, MoviesHorizontalAdapter.HorizontalViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val binding = HorizontalItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        when (holder) {
            else -> {
                holder.bindBestSellerView(getItem(position), holder.itemView.context)
            }
        }
    }

    inner class HorizontalViewHolder(private val binding: HorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindBestSellerView(movie: MoviePoster, context: Context) {
            binding.bestSellerText.text = movie.title
//            val imgLoader = ImageLoader.Builder(context)
//                .memoryCachePolicy(CachePolicy.ENABLED)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .build()
//            binding.bestSellerImage.load(movie.posterImageUrl,imgLoader)

            val request = ImageRequest.Builder(binding.bestSellerImage.context)
                .data(movie.posterImageUrl)
                .dispatcher(Dispatchers.IO)
                .memoryCacheKey(movie.posterImageUrl)
                .diskCacheKey(movie.posterImageUrl)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
            context.imageLoader.enqueue(request)

            /*Glide.with(context)
                .load(movie.posterImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.bestSellerImage)*/
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
}