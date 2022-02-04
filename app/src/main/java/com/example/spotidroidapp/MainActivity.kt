package com.example.spotidroidapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.spotidroidapp.connector.UserService
import com.example.spotidroidapp.connector.VolleyCallBack
import com.example.spotidroidapp.fragment.AuthFragmentDirections
import com.example.spotidroidapp.fragment.MainFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.AuthorizationResponse.Type




class MainActivity : AppCompatActivity() {

    private var queue: RequestQueue? = null
    private var bottomNav: BottomNavigationView? = null
    private var msPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null


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

    override fun onStart() {
        super.onStart()
        msPreferences = getSharedPreferences("SPOTIFY", 0)
        queue = Volley.newRequestQueue(applicationContext)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                Type.TOKEN -> {
                    editor = getSharedPreferences("SPOTIFY", 0).edit()
                    editor?.putString("token", response.accessToken)
                    editor?.apply()
                    Log.i("Auth", "Connected")
                    waitForUserInfo()
                }
                Type.ERROR -> {

                }
                else -> {
                }
            }
        }
    }

    private fun waitForUserInfo() {
        val userService = UserService(queue!!, msPreferences!!)
        userService[object : VolleyCallBack {
            override fun onSuccess() {
                val user = userService.user
                editor = getSharedPreferences("SPOTIFY", 0).edit()
                editor?.putString("userid", user?.id)
                editor?.commit()
                setUpNavigation()
            }
        }]
    }

    fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController!!)
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
        appBar.setupWithNavController(navController)

        val action = AuthFragmentDirections.actionToMain()

        navController.navigate(action)
    }

}