package com.example.spotidroidapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spotidroidapp.databinding.GenresCardBinding
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.Genres
import com.example.spotidroidapp.ui.listener.OnGenreClickListener
import com.example.spotidroidapp.ui.viewholder.GenreViewHolder


class GAdapter(private val clickListener: OnGenreClickListener)
    : ListAdapter<Genre, GenreViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GenresCardBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val pos = getItem(position)
        holder.bind(pos)

        holder.itemView.setOnClickListener {
            clickListener.onClick(pos)
            true
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }

}