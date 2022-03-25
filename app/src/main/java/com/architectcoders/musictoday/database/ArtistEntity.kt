package com.architectcoders.musictoday.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ArtistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val biography: String,
    val publishingDate: String,
    val imageUrl: String
) : Parcelable