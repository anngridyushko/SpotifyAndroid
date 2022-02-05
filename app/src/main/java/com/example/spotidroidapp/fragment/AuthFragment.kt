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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("AuthFragment", "Created")
    }
}