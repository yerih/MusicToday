package com.architectcoders.musictoday

import android.app.Application
import androidx.room.Room
import com.architectcoders.musictoday.framework.database.ArtistDatabase

class App: Application(){

    lateinit var db: ArtistDatabase
    private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            ArtistDatabase::class.java, "artist-db"
        ).build()
    }
}

