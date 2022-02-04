package com.example.spotidroidapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import com.example.spotidroidapp.R
import kotlin.random.Random


class GenreAdapter(private val names: List<String>) :
    RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImageView: ImageView? = null
        var titleTextView: TextView? = null


        fun bindData(title: String){
            val rnd = Random
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            cardImageView?.setColorFilter(color)
            titleTextView?.text = title
        }

        init {
            cardImageView = itemView.findViewById(R.id.genre_image)
            titleTextView = itemView.findViewById(R.id.genre_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.genres_card, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(names[position])
    }

    override fun getItemCount(): Int {
        return names.size
    }
}