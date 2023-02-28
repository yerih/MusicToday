package com.architectcoders.musictoday.data.server


import com.architectcoders.musictoday.ui.main.ArtistSearch
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MusicService {

    companion object{
        const val urlLastFm = "http://ws.audioscrobbler.com/2.0/"
        private const val apiKeyLastFm = "7aa4aa6843767418d5fe6a88aea85321"//"03ebd6204944f82b3a5a51c2b3309ecf"
        private val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }).build()

        private val gson = GsonBuilder().setLenient().create()

//        private val builder = buildRetrofitWith(urlLastFm)
        fun buildRetrofitWith(url: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

//        val service: MusicService = buildRetrofitWith(urlLastFm).create()//builder.create()
    }

    @GET("https://api.deezer.com/chart/0/artists")
    suspend fun getPopularArtists(): PopularArtists

    @GET("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&api_key=$apiKeyLastFm&format=json")
    suspend fun getArtistInfo(@Query("artist") artist: String): ArtistInfo

    @GET("http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&api_key=$apiKeyLastFm&format=json")
    suspend fun getArtistByLocation(@Query("country") country: String): ArtistsByLocation

    @GET("https://api.deezer.com/search/artist?")
    suspend fun getSearchArtist(@Query("q") artist: String) : ArtistSearch

}
