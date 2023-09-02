package com.architectcoders.musictoday.data.server.musicovery

import com.architectcoders.musictoday.domain.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val URL_MUSICOVERY = "http://musicovery.com/api/V6/"
const val SEARCH_ENDPOINT_MUSICOVERY = "artist.php?fct=search"

interface MusicoveryService{


    companion object{
        private const val urlMusicovery = URL_MUSICOVERY
//        private val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//            OkHttpClient.Builder().addInterceptor(this).build()
//        }).build()

//        private val gson = GsonBuilder().setLenient().create()

//        fun buildRetrofitWith(url: String = urlMusicovery, client: OkHttpClient = okHttpClient): Retrofit {
//            return Retrofit.Builder()
//                .baseUrl(url)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//        }

    }

    @GET(SEARCH_ENDPOINT_MUSICOVERY)
    suspend fun searchMusicovery(@Query("artistName") artist: String) : Response<SearchMusicoveryResponse>

}