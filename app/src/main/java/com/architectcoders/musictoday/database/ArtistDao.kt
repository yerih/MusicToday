package com.architectcoders.musictoday.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao{
    @Query("SELECT * FROM ArtistEntity")
    fun getAll(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ArtistEntity WHERE id = :id")
    fun findById(id: Int) : Flow<ArtistEntity>

    @Query("SELECT COUNT(id) FROM ArtistEntity")
    fun artistCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtists(artists: List<ArtistEntity>)

    @Update
    fun updateArtist(artist: ArtistEntity)
}


