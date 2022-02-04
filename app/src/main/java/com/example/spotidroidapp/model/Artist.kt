package com.example.spotidroidapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    var id: String,
    var name: String
) : Parcelable