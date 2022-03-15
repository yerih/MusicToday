package com.architectcoders.musictoday.model


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MusicService {

    companion object{
        private const val urlRoot = "http://ws.audioscrobbler.com/2.0/"
        private const val apiKeyLastFm = "7aa4aa6843767418d5fe6a88aea85321"//"03ebd6204944f82b3a5a51c2b3309ecf"

        private val gson = GsonBuilder().setLenient().create()

        private val builder = Retrofit.Builder()
            .baseUrl(urlRoot)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service: MusicService = builder.create()
    }

    @GET("https://api.deezer.com/chart/0/artists")
    suspend fun getPopularArtists(): PopularArtists

    @GET("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&api_key=$apiKeyLastFm&format=json")
    suspend fun getArtistInfo(@Query("artist") artist: String): ArtistInfo

}
