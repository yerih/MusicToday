package com.architectcoders.musictoday.model


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET


interface DeezerService {

    companion object{
        private const val urlRoot = "http://ws.audioscrobbler.com/2.0/"

        private val builder = Retrofit.Builder()
            .baseUrl(urlRoot)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: DeezerService = builder.create()
    }

    @GET("https://api.deezer.com/chart/0/artists")
    suspend fun getPopularArtists(): PopularArtists

}
