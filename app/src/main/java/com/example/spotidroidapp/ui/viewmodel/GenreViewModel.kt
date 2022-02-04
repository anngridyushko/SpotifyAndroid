package com.example.spotidroidapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.SongRepository

class GenreViewModel(private val repository: SongRepository) : ViewModel() {
    val genres = liveData {
        emit(repository.getGenres().genres.map { Genre(it) })
    }

    private val _navigateToSelected = MutableLiveData<Genre?>()
    val navigateToSelected: LiveData<Genre?>
        get() = _navigateToSelected

    fun displaySelected(genre: Genre?) {
        _navigateToSelected.value = genre
    }

    fun hideSelected() {
        _navigateToSelected.value = null
    }
}