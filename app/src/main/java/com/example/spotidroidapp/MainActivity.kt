package com.example.spotidroidapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.spotidroidapp.connector.UserService
import com.example.spotidroidapp.connector.VolleyCallBack
import com.example.spotidroidapp.fragment.AuthFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse


class MainActivity : AppCompatActivity() {

    private var bottomNav: BottomNavigationView? = null
    var mSpotifyAppRemote: SpotifyAppRemote? = null

    private val CLIENT_ID = "e313f45636e943e19f6edc2787533423"
    private val REDIRECT_URI = "spotidroidapp://callback"
    private val SCOPES = "user-library-read,user-read-recently-played,user-library-modify,user-read-email,user-read-private"
    private val REQUEST_CODE = 1133

    private var msPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    Log.d("MainActivity", "Spotify remote connected")
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("MainActivity", throwable.message, throwable)
                }
            })

    }

    override fun onStart() {
        super.onStart()
        msPreferences = getSharedPreferences("SPOTIFY", 0)
        queue = Volley.newRequestQueue(applicationContext)
        setUpNavigation()
        val builder =
            AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(arrayOf(SCOPES))
        val request = builder.build()
        Log.i("MainActivity", "Trying to log in")
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    editor = getSharedPreferences("SPOTIFY", 0)?.edit()
                    editor?.putString("token", response.accessToken)
                    editor?.apply()
                    Log.i("MainActivity", "Connected")
                    waitForUserInfo()
                }
                AuthorizationResponse.Type.ERROR -> {


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
                editor = getSharedPreferences("SPOTIFY", 0)?.edit()
                editor?.putString("userid", user?.id)
                editor?.commit()
                val action = AuthFragmentDirections.actionToMain()
                Log.i("MainActivity", getSharedPreferences("SPOTIFY", 0)?.getString("token", "")!!
                )
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(action)
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
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
                R.id.libraryFragment,
                R.id.searchFragment
            )
        )
        appBar.setupWithNavController(navController, appBarConfiguration)


    }

    fun playSong(string: String) {
        mSpotifyAppRemote?.playerApi?.play(string)
        Log.d("PLAY", string)
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }

}