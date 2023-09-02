package com.architectcoders.musictoday.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.architectcoders.musictoday.data.database.ArtistDatabase
import com.architectcoders.musictoday.data.database.ArtistRoomDataSource
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.data.server.MusicService
import com.architectcoders.musictoday.data.server.musicovery.MusicoveryService
import com.architectcoders.musictoday.data.server.musicovery.URL_MUSICOVERY
import com.architectcoders.musictoday.ui.common.LocationDataSource
import com.architectcoders.musictoday.ui.common.LocationManager
import com.architectcoders.musictoday.ui.common.PlayServicesLocationDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    @ApiKey
//    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        ArtistDatabase::class.java,
        "artist-db"
    ).build()

    @Provides
    @Singleton
    fun provideArtistDao(db: ArtistDatabase) = db.ArtistDao()

    @Provides
    @Singleton
    fun provideLocationDataSource(app: Application): LocationDataSource = PlayServicesLocationDataSource(app)

    @Provides
    @Singleton
    fun provideMusicService(): MusicService = MusicService.buildRetrofitWith().create()

    @Provides
    @Singleton
    fun provideMusicoveryService(): MusicoveryService = MusicService.buildRetrofitWith(URL_MUSICOVERY).create()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: ArtistRoomDataSource): ArtistLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: ArtistServerDataSource): ArtistRemoteDataSource

//    @Binds
//    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

//    @Binds
//    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker

}