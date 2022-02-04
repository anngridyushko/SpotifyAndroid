package com.example.spotidroidapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.SongRepository

class GenreSongViewModelFactory(
    private val repository: SongRepository,
    private val genre: Genre? = null
    ) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenreSongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GenreSongViewModel(repository, genre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}