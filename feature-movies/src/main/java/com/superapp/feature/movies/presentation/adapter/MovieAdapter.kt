package com.superapp.feature.movies.presentation.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superapp.feature.movies.R
import com.superapp.feature.movies.databinding.FragmentMoviesListBinding
import com.superapp.feature.movies.databinding.HorizontalItemBinding
import com.superapp.feature.movies.databinding.ItemHorizontalBinding
import com.superapp.feature.movies.databinding.ItemVerticalBinding
import com.superapp.feature.movies.domain.model.CollectionEntity
import com.superapp.feature.movies.domain.model.MoviePoster

sealed class ListItem {
    data class VerticalItem(val horizontalItems: List<MoviePoster>) : ListItem()
    data class HorizontalItem(val data: String) : ListItem()
}

internal class MovieAdapter(private val items: List<ListItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_VERTICAL = 1
        private const val VIEW_TYPE_HORIZONTAL = 2
    }

    private var oldMoviesList = emptyList<CollectionEntity>()

    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var bindingChild: HorizontalItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_VERTICAL -> {
                val binding = ItemVerticalBinding.inflate(layoutInflater, parent, false)
                VerticalViewHolder(binding)
            }
            VIEW_TYPE_HORIZONTAL -> {
                val binding = ItemHorizontalBinding.inflate(layoutInflater, parent, false)
                HorizontalViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.VerticalItem -> VIEW_TYPE_VERTICAL
            is ListItem.HorizontalItem -> VIEW_TYPE_HORIZONTAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder.itemViewType) {
            VIEW_TYPE_VERTICAL -> {
                val verticalHolder = holder as VerticalViewHolder
                val verticalItem = item as ListItem.VerticalItem
                verticalHolder.bind(verticalItem.horizontalItems)
            }
            VIEW_TYPE_HORIZONTAL -> {
                val horizontalHolder = holder as HorizontalViewHolder
                val horizontalItem = item as ListItem.HorizontalItem
                horizontalHolder.bind(horizontalItem.data)
            }
        }
    }

//        val childRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.childRecyclerView)
//        val key = getSectionID(holder.layoutPosition)
//        val state = scrollStates[key]
//        if (state != null) {
//            childRecyclerView.layoutManager?.onRestoreInstanceState(state)
//        } else {
//            childRecyclerView.layoutManager?.scrollToPosition(0)
//        }

    private fun getSectionID(position: Int): String {
        return oldMoviesList[position].genre.id
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] =
            holder.itemView.findViewById<RecyclerView>(R.id.childRecyclerView).layoutManager?.onSaveInstanceState()
        super.onViewRecycled(holder)
    }

    fun setData(newsMovies: List<CollectionEntity>) {
        val diffUtil = MovieDiffCallback(oldMoviesList, newsMovies)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldMoviesList = newsMovies
        diffResults.dispatchUpdatesTo(this)
    }

    inner class VerticalViewHolder(private val binding: ItemVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var horizontalAdapter: MoviesHorizontalAdapter
        fun bind(horizontalItems: List<MoviePoster>) {
            // Установка LayoutManager для горизонтальной RecyclerView
            binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            // Установка адаптера для горизонтальной RecyclerView
            horizontalAdapter = MoviesHorizontalAdapter()
            horizontalAdapter.submitList(horizontalItems)
            binding.recyclerViewHorizontal.adapter = horizontalAdapter
        }
    }

    // ViewHolder for horizontal items
    inner class HorizontalViewHolder(private val binding: ItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.textView.text = data
        }
}

//    class VerticalViewHolder(binding: ItemVerticalBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        private val viewPool = RecyclerView.RecycledViewPool()
//
//        fun bind(genreEntity: GenreEntity, openMovieCallback: (String) -> Unit) {
////            binding.childRecyclerView.setHasFixedSize(true)
////            binding.childRecyclerView.layoutManager =
////                LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
////            binding.childRecyclerView.setRecycledViewPool(viewPool)
////            val adapter = MoviesHorizontalAdapter()
////            adapter.submitList(collectionEntity.movies)
////            binding.childRecyclerView.adapter = adapter
////            binding.collectionTitle.text = collectionEntity.genre.name
//        }
//    }
//
//    inner class HorizontalViewHolder(private val binding: ItemHorizontalBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bindBestSellerView(movie: MoviePoster) {
//            binding.bestSellerText.text = movie.title
//            val imgLoader = ImageLoader.Builder(context)
//                .memoryCachePolicy(CachePolicy.ENABLED)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .build()
//            binding.bestSellerImage.load(movie.posterImageUrl,imgLoader)
//
//            val request = ImageRequest.Builder(binding.bestSellerImage.context)
//                .data(movie.posterImageUrl)
//                .dispatcher(Dispatchers.IO)
//                .memoryCacheKey(movie.posterImageUrl)
//                .diskCacheKey(movie.posterImageUrl)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .memoryCachePolicy(CachePolicy.ENABLED)
//                .build()
//            context.imageLoader.enqueue(request)
//
//            /*Glide.with(context)
//                .load(movie.posterImageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .into(binding.bestSellerImage)*/
//        }
//    }
}

internal class MovieDiffCallback(
    private val oldList: List<CollectionEntity>,
    private val newList: List<CollectionEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return oldProduct == newProduct
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList
        val newProduct = newList
        return oldProduct == newProduct && oldProduct == newProduct
    }
}