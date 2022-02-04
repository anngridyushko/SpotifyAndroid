package com.example.spotidroidapp.spotiapi

import com.example.spotidroidapp.model.Song

class Songs<T> private constructor(
    val items: List<Track<T>>
)

class Track<T> private constructor(
    val track: Song
)