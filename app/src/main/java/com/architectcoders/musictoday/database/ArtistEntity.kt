package com.architectcoders.musictoday.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtistDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val biography: String,
    val publishingDate: String,
)