package com.architectcoders.musictoday.di

import androidx.lifecycle.SavedStateHandle
import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object GeneralModule {

    @Id
    @Provides
    fun provideArtistId(savedStateHandle: SavedStateHandle): Int = savedStateHandle.get<Int>("id") ?: throw IllegalStateException("error artist id null")


    @Provides
    fun provideFindArtistUseCase(artistRepository: ArtistRepository): FindArtistByIdUseCase = FindArtistByIdUseCase(artistRepository)

    @Provides
    fun provideGetArtistInfoUseCase(artistRepository: ArtistRepository): GetArtistInfoUseCase = GetArtistInfoUseCase(artistRepository)

}