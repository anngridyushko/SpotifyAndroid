package com.example.spotidroidapp.connector

import com.android.volley.AuthFailureError
import com.example.spotidroidapp.model.User
import com.google.gson.Gson
import com.android.volley.toolbox.JsonObjectRequest
import android.content.SharedPreferences
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import org.json.JSONObject

class UserService(
    private val mqueue: RequestQueue,
    private val msharedPreferences: SharedPreferences
) {
    var user: User? = null
        private set

    operator fun get(callBack: VolleyCallBack) {


        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            ENDPOINT, null,
            Response.Listener { response: JSONObject ->
                val gson = Gson()
                user = gson.fromJson(
                    response.toString(),
                    User::class.java
                )
                callBack.onSuccess()
            },
            Response.ErrorListener {_: VolleyError? ->  get(callBack)}) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val token = msharedPreferences.getString("token", "")
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                return headers
            }
        }
        mqueue.add(jsonObjectRequest)
    }

    companion object {
        private const val ENDPOINT = "https://api.spotify.com/v1/me"
    }
}