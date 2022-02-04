package com.example.spotidroidapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.Song
import com.example.spotidroidapp.model.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreSongViewModel(private val repository: SongRepository, genre: Genre? = null): ViewModel() {
    private val _selected = MutableLiveData<Genre?>()
    val selected: LiveData<Genre?>
        get() = _selected

    init {
        _selected.value = genre
    }

    val songs = liveData {
        val num = 50
        val limit = 50
        val tmp = mutableListOf<Song>()
        val likes = mutableListOf<Boolean>()
        for(i in 0 until num step limit) {
            val part = repository.getGenreSongs(selected.value!!.name, limit)
            likes.addAll(repository.isItLiked(part.joinToString(",") { it.id }))
            part.mapIndexed { index, song -> if(likes[index]) song.liked = true }
            tmp.addAll(part)

        }

        emit(tmp)
    }

    val searchedSongs : MutableLiveData<List<Song>> by lazy {
        MutableLiveData<List<Song>>()
    }

    fun searchSongs(str: String) {
        str?.let {
            if(it != "") {
                val tmp = mutableListOf<Song>()
                val likes = mutableListOf<Boolean>()
                CoroutineScope(Dispatchers.IO).launch {
                    val part = repository.searchSongs(it)
                    likes.addAll(repository.isItLiked(part.joinToString(",") { it.id }))
                    part.mapIndexed { index, song -> if (likes[index]) song.liked = true }
                    tmp.addAll(part)
                    searchedSongs.postValue(tmp)
                }
            }
        }
    }

    val liked = liveData {
        val num = 50
        val limit = 50
        val tmp = mutableListOf<Song>()
        for(i in 0 until num step limit) {
            val part = repository.getLikedSongs(limit)
            part.map { song -> song.liked = true }
            tmp.addAll(part)
        }
        emit(tmp)
    }

    fun likeSong(s: Song) {
        var job = CoroutineScope(Dispatchers.IO).launch {
            repository.likeSong(s.id)
            s.liked = true
        }
    }

    fun dislikeSong(s: Song) {
        var job = CoroutineScope(Dispatchers.IO).launch {
            repository.dislikeSong(s.id)
            s.liked = false
        }
    }

}