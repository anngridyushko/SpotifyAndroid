package com.example.spotidroidapp.connector

import android.content.Context
import org.json.JSONException
import com.example.spotidroidapp.model.Song
import org.json.JSONObject
import com.google.gson.Gson
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.content.SharedPreferences
import com.android.volley.*
import com.android.volley.toolbox.StringRequest


class SongService(context: Context) {
    val songs: ArrayList<Song> = ArrayList()
    private val sharedPreferences: SharedPreferences
    private val queue: RequestQueue
    fun getRecentlyPlayedTracks(callBack: VolleyCallBack): ArrayList<Song> {
        val endpoint = "https://api.spotify.com/v1/me/player/recently-played"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Request.Method.GET, endpoint, null,
                Response.Listener { response: JSONObject ->
                    val gson = Gson()
                    val jsonArray = response.optJSONArray("items")
                    for (n in 0 until jsonArray.length()) {
                        try {
                            var `object` = jsonArray.getJSONObject(n)
                            `object` = `object`.optJSONObject("track")
                            val song =
                                gson.fromJson(`object`.toString(), Song::class.java)
                            songs.add(song)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    callBack.onSuccess()
                },
                Response.ErrorListener { error: VolleyError? -> }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: MutableMap<String, String> = HashMap()
                    val token = sharedPreferences.getString("token", "")
                    val auth = "Bearer $token"
                    headers["Authorization"] = auth
                    return headers
                }
            }

        queue.add(jsonObjectRequest)


        return songs
    }

    init {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0)
        queue = Volley.newRequestQueue(context)
    }
}