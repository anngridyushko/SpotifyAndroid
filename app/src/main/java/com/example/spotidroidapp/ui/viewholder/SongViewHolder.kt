package com.example.spotidroidapp.ui.viewholder

import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.spotidroidapp.R
import com.example.spotidroidapp.databinding.GenresCardBinding
import com.example.spotidroidapp.databinding.SongCardBinding
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.Song
import kotlin.random.Random

class SongViewHolder(private val binding: SongCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var chbox: CheckBox = itemView.findViewById(R.id.song_like)
    var currentSong: Song? = null
        private set


    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun bindImage(imgView: ImageView, imgUrl: String?) {
            imgUrl?.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

                Glide.with(imgView.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(imgView)
            }
        }
    }

    fun bind(song: Song) {
        currentSong = song
        with(binding) {
            songName.text = song.name
            songArtist.text = song.artists.joinToString("\n") { it.name }
            songLike.isChecked = song.liked
            var url = song.album.images[0].url
            url?.let {
                val imgUri = url.toUri().buildUpon().scheme("https").build()

                Glide.with(songImage.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(songImage)
            }
            if(song.liked) {
                songLike.isChecked = true

            }

        }
    }
}

