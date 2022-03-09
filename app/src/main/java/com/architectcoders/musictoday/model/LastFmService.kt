package com.architectcoders.musictoday.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LastFmService {

    companion object{
        private const val urlRoot = "http://ws.audioscrobbler.com/2.0/"
        private const val apiKey  = "7aa4aa6843767418d5fe6a88aea85321"

        private val builder = Retrofit.Builder()
            .baseUrl(urlRoot)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: LastFmService = builder.create()
    }

    @POST("?method=chart.gettopartists&api_key=$apiKey&format=json")
    suspend fun artistOfToday(): ArtistLastFmResult

}



