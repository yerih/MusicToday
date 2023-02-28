package com.architectcoders.musictoday.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.appTestShared.FakeArtistDao
import com.architectcoders.appTestShared.FakeLocationHelper
import com.architectcoders.appTestShared.FakeRemoteService
import com.architectcoders.appTestShared.buildArtistDB
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.database.ArtistDatabase
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
import retrofit2.create
import javax.inject.Singleton


@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
class TestAppModule {

    @Provides
    @Singleton
    fun provideArtistDao(db: ArtistDatabase): ArtistDao = db.ArtistDao()

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        ArtistDatabase::class.java,
    ).build()

    @Provides
    @Singleton
    fun provideLocationDataSource(): LocationDataSource = FakeLocationHelper()

    @Provides
    @Singleton
    @UrlTesting
    fun provideUrlTesting(): String = "http://localhost:8080"

    @Provides
    @Singleton
    fun provideMusicService(@UrlTesting url: String): MusicService = MusicService.buildRetrofitWith(url).create()


}

