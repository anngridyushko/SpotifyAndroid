package com.example.spotidroidapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotiImage(
    var height: Int,
    var url: String,
    var width: Int
) : Parcelable