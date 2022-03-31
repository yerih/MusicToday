package com.architectcoders.musictoday.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao{
    @Query("SELECT * FROM ArtistEntity")
    fun getAll(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ArtistEntity WHERE id = :id")
    fun findById(id: Int) : Flow<ArtistEntity>

    @Query("SELECT COUNT(id) FROM ArtistEntity")
    suspend fun artistCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    @Update
    suspend fun updateArtist(artist: ArtistEntity)
}


