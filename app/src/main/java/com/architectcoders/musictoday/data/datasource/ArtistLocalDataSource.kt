package com.architectcoders.musictoday.data.datasource

import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow


interface ArtistLocalDataSource {
    val artists: Flow<List<Artist>>
    fun findById(id: Int): Flow<Artist>
    suspend fun isEmpty(): Boolean
    suspend fun save(artists: List<Artist>)
}