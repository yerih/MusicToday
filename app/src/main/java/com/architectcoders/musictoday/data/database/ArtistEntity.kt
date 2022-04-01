package com.architectcoders.musictoday.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ArtistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val biography: String = "",
    val publishingDate: String = "",
    val imageUrl: String = "",
    val favorite: Boolean = false
) : Parcelable

fun List<ArtistEntity>.toDomainModel(): List<com.architectcoders.musictoday.domain.Artist> = map { it.toDomainModel() }
fun ArtistEntity.toDomainModel(): com.architectcoders.musictoday.domain.Artist =
    com.architectcoders.musictoday.domain.Artist(
        id,
        name,
        biography,
        publishingDate,
        imageUrl,
        favorite
    )
