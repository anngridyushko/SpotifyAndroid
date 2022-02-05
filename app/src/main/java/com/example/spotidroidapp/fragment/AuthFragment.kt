package com.example.spotidroidapp.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.RequestQueue
import com.example.spotidroidapp.R
import com.example.spotidroidapp.connector.UserService
import com.example.spotidroidapp.connector.VolleyCallBack
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class AuthFragment: Fragment(R.layout.fragment_auth) {
    private val CLIENT_ID = "e313f45636e943e19f6edc2787533423"
    private val REDIRECT_URI = "com.example.spotidroidapp://callback"
    private val SCOPES = "user-library-read,user-read-recently-played,user-library-modify,user-read-email,user-read-private"
    private val REQUEST_CODE = 1133

    private var msPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }






}