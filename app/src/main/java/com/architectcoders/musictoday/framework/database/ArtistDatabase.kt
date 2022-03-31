package com.architectcoders.musictoday.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtistEntity::class], version = 1, exportSchema = false)
abstract class ArtistDatabase: RoomDatabase(){
    abstract fun ArtistDao(): ArtistDao
}


