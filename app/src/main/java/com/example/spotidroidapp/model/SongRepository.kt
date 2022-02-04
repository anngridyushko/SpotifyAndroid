package com.example.spotidroidapp.model

import com.example.spotidroidapp.spotiapi.SpotiApi
import com.example.spotidroidapp.spotiapi.SpotiApiService
import com.google.gson.Gson
import retrofit2.awaitResponse

class SongRepository(val token: String) {
    var client: SpotiApiService = SpotiApi(token).retrofitService
    suspend fun getLikedSongs(limit: Int = 20) = client.getLikedSongs(limit).items.map { it.track }
    suspend fun getGenres() = client.getGenres()
    suspend fun getGenreSongs(genre: String, limit: Int = 20) = client.getGenreSongs("genre:$genre", limit).tracks.items
    suspend fun isItLiked(str: String) = client.isItLiked(str).toString().replace("[^a-z,]".toRegex(), "").split(",").map { it.toBoolean() }
    suspend fun likeSong(str: String) = client.likeSong(str)
    suspend fun dislikeSong(str: String) = client.dislikeSong(str)
    suspend fun searchSongs(str: String) = client.searchSongs(str).tracks.items
}