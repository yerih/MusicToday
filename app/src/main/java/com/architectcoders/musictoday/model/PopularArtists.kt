package com.architectcoders.musictoday.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PopularArtists(
    @SerializedName("data") val artists: List<Data>,
    val total: Int
) : Parcelable {
    @Parcelize
    data class Data(
        val id: Int,
        val link: String,
        val name: String,
        val picture: String,
        val picture_big: String,
        val picture_medium: String,
        val picture_small: String,
        val picture_xl: String,
        val position: Int,
        val radio: Boolean,
        val tracklist: String,
        val type: String
    ) : Parcelable
}