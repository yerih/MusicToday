package com.architectcoders.appTestShared

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.data.database.ArtistRoomDataSource
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.data.database.ArtistEntity as ArtistDB


fun buildArtistRepository(
    localData: List<ArtistDB> = emptyList(),
    remoteData: List<ArtistDB> = emptyList(),
    country: String? = null
): ArtistRepository {
    val localDS = ArtistRoomDataSource(FakeArtistDao(localData))
    val remoteDS = ArtistServerDataSource(FakeLocationHelper(country), FakeRemoteService(remoteData))
    return ArtistRepository(localDS, remoteDS)
}


fun buildArtistDB(vararg id: Int) = id.map{
    ArtistDB(
        id = it,
        favorite = false,
        biography = "biography $it",
        imageUrl = "imageURL $it",
        name = "name $it",
        publishingDate = "publishDate $it"
    )
}






