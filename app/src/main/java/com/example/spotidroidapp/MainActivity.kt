package com.example.spotidroidapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.AuthorizationResponse.Type




class MainActivity : AppCompatActivity() {

    private val CLIENT_ID = "e313f45636e943e19f6edc2787533423"
    private val REDIRECT_URI = "http://localhost:8888/callback"
    private val SCOPES = "user-library-read,user-read-recently-played,user-library-modify,user-read-email,user-read-private"
    private val REQUEST_CODE = 1133

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder =
            AuthorizationRequest.Builder(CLIENT_ID, Type.TOKEN, REDIRECT_URI)
        builder.setScopes(arrayOf(SCOPES))
        val request = builder.build()
        Log.i("Auth", "Trying to log in")
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                Type.TOKEN -> {
                    Log.i("Auth", "Connected")
                }
                Type.ERROR -> {
                }
                else -> {
                }
            }
        }
    }
}