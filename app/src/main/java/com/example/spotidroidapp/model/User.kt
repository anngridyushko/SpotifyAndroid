package com.example.spotidroidapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (
    var id: String?,
    var email: String?,
    var country: String?,
    var display_name: String?,
    var birthdate: String?
    ): Parcelable