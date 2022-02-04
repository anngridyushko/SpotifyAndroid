package com.example.spotidroidapp.ui.listener

import android.view.View
import com.example.spotidroidapp.model.Song


interface OnSongClickListener {
    fun onClick(v: View, s: Song)
    fun onClick(s: Song)
}
