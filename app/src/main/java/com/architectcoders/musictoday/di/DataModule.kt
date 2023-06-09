package com.architectcoders.musictoday.di

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.database.ArtistRoomDataSource
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.data.server.MusicService
import com.architectcoders.musictoday.ui.common.LocationDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule{


    @Provides
    fun provideArtistRepository(
        localDataSource: ArtistLocalDataSource,
        remoteDataSource: ArtistRemoteDataSource
    ): ArtistRepository = ArtistRepository(localDataSource, remoteDataSource)


}