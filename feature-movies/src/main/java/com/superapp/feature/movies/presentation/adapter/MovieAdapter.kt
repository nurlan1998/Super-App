package com.superapp.feature.movies.presentation.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superapp.feature.movies.R
import com.superapp.feature.movies.databinding.MoviesHorizontalListBinding
import com.superapp.feature.movies.domain.model.CollectionEntity

internal class MovieAdapter(private val openMovieCallback: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldMoviesList = emptyList<CollectionEntity>()

    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            MoviesHorizontalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalViewHolder(binding)
    }

    override fun getItemCount(): Int = oldMoviesList.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VerticalViewHolder -> {
                holder.bind(oldMoviesList[position], openMovieCallback)
            }
        }

        val childRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.childRecyclerView)
        val key = getSectionID(holder.layoutPosition)
        val state = scrollStates[key]
        if (state != null) {
            childRecyclerView.layoutManager?.onRestoreInstanceState(state)
        } else {
            childRecyclerView.layoutManager?.scrollToPosition(0)
        }
    }

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

    class VerticalViewHolder(private val binding: MoviesHorizontalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewPool = RecyclerView.RecycledViewPool()

        fun bind(collectionEntity: CollectionEntity, openMovieCallback: (String) -> Unit) {
            binding.childRecyclerView.setHasFixedSize(true)
            binding.childRecyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            binding.childRecyclerView.setRecycledViewPool(viewPool)
            val adapter = MoviesHorizontalAdapter()
            adapter.submitList(collectionEntity.movies)
            binding.childRecyclerView.adapter = adapter
            binding.collectionTitle.text = collectionEntity.genre.name
        }
    }
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