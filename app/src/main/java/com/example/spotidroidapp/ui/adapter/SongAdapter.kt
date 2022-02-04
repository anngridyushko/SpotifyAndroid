package com.example.spotidroidapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.spotidroidapp.databinding.SongCardBinding
import com.example.spotidroidapp.model.Song
import com.example.spotidroidapp.ui.listener.OnSongClickListener
import com.example.spotidroidapp.ui.viewholder.SongViewHolder

class SongAdapter(private val clickListener: OnSongClickListener)
    : ListAdapter<Song, SongViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SongCardBinding.inflate(layoutInflater, parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val pos = getItem(position)
        holder.bind(pos)

        holder.chbox.setOnClickListener {
            clickListener.onClick(pos)
        }
        holder.itemView.setOnClickListener {
            clickListener.onClick(holder.itemView, pos)
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }
        }
    }

}