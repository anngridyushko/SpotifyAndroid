package com.example.spotidroidapp.ui.viewholder

import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spotidroidapp.databinding.GenresCardBinding
import com.example.spotidroidapp.model.Genre
import kotlin.random.Random

class GenreViewHolder(private val binding: GenresCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var currentGenre: Genre? = null
        private set

    companion object {
        @JvmStatic
        @BindingAdapter("app:tint")
        fun setImageTint(view: ImageView, c: Color?) {
            c?.let {
                val color = argb(c.alpha(), c.red(), c.green(), c.blue())
                view.setColorFilter(color)
            }

        }
    }
    fun bind(genre: Genre) {
        currentGenre = genre

        with(binding) {
            genreName.text = genre.name
            val rnd = Random
            val color = ColorDrawable(Color.argb(
                255,
                rnd.nextInt(200),
                rnd.nextInt(200),
                rnd.nextInt(200)))
            genreImage.setImageDrawable(color)

        }
    }
}

