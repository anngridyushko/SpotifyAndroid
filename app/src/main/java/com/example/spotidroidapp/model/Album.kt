package com.example.spotidroidapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    var id: String,
    var name: String,
    var images: List<SpotiImage>
) : Parcelable