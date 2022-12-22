package com.architectcoders.musictoday.ui.detail.di

import androidx.lifecycle.SavedStateHandle
import com.architectcoders.musictoday.di.ArtistId
import com.architectcoders.musictoday.ui.detail.DetailFragmentArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailModule{

    @Provides
    @ViewModelScoped
    @ArtistId
    fun provideArtistId(savedStateHandle: SavedStateHandle) = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).artistId

}