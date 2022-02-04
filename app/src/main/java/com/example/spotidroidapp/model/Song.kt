package com.example.spotidroidapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    var id: String,
    var name: String,
    var artists: List<Artist>,
    var album: Album,
    var liked: Boolean = false,
    var uri: String
    ) : Parcelable
