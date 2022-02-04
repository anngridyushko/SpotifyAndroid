package com.example.spotidroidapp.spotiapi


import com.example.spotidroidapp.model.Genres
import com.example.spotidroidapp.model.Song
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import retrofit2.http.*

private const val BASE_URL = "https://api.spotify.com/v1/"

interface SpotiApiService {
    @GET("tracks/11dFghVXANMlKmJXsNCbNl")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun getRecentTracks(
        @Query("limit") limit: Int = 1
    ): List<Song>

    @GET("recommendations/available-genre-seeds")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun getGenres(): Genres

    @GET("search?type=track")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun getGenreSongs(
        @Query("query") genre: String,
        @Query("limit") limit: Int = 20
    ): GSongs<List<Song>>

    @GET("me/tracks")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun getLikedSongs(
        @Query("limit") limit: Int = 20
    ): Songs<List<Song>>

    @GET("me/tracks/contains")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun isItLiked(
        @Query("ids") ids: String
    ): List<Boolean>

    @PUT("me/tracks")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun likeSong(
        @Query("ids") ids: String
    ): retrofit2.Response<Void>

    @DELETE("me/tracks")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun dislikeSong(
        @Query("ids") ids: String
    ): retrofit2.Response<Void>

    @GET("search?type=track")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun searchSongs(
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): GSongs<List<Song>>
}

class SpotiApi(val token: String?) {

    var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }.build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val retrofitService: SpotiApiService by lazy {
        retrofit.create(SpotiApiService::class.java)
    }


}