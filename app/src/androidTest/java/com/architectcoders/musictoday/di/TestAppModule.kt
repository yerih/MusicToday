package com.architectcoders.musictoday.di

import com.architectcoders.appTestShared.FakeArtistDao
import com.architectcoders.appTestShared.FakeLocationHelper
import com.architectcoders.appTestShared.FakeRemoteService
import com.architectcoders.appTestShared.buildArtistDB
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.database.ArtistRoomDataSource
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.data.server.MusicService
import com.architectcoders.musictoday.ui.common.LocationDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
class TestAppModule {

    @Provides
    @Singleton
    fun provideArtistDao(): ArtistDao = FakeArtistDao()

    @Provides
    @Singleton
    fun provideLocationDataSource(): LocationDataSource = FakeLocationHelper()

    @Provides
    @Singleton
    fun provideMusicService(): MusicService = FakeRemoteService(buildArtistDB(1,2,3,4,5,6))


}

