package com.architectcoders.musictoday.framework.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.musictoday.domain.Artist
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

fun List<ArtistEntity>.toDomainModel(): List<Artist> = map { it.toDomainModel() }
fun ArtistEntity.toDomainModel(): Artist = Artist(id, name, biography, publishingDate, imageUrl, favorite)
