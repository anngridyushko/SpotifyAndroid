package com.example.spotidroidapp.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Genre(
    val name: String,
    var color: @RawValue Color? = null
): Parcelable

@Parcelize
data class Genres(
    var genres: MutableList<String>
) : Parcelable
