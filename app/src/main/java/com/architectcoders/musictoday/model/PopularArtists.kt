package com.architectcoders.musictoday.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PopularArtists(
    @SerializedName("data") val artists: List<Artist>,
    val total: Int
) : Parcelable {
    @Parcelize
    data class Artist(
        val id: Int = 0,
        val link: String = "",
        val name: String = "",
        val picture: String = "",
        val picture_big: String = "",
        val picture_medium: String = "",
        val picture_small: String = "",
        val picture_xl: String = "",
        val position: Int = 0,
        val radio: Boolean = true,
        val tracklist: String = "",
        val type: String = ""
    ) : Parcelable
}