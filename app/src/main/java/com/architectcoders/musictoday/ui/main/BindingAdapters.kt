package com.architectcoders.musictoday.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.PopularArtists


@BindingAdapter("items")
fun RecyclerView.setItems(artists: List<ArtistEntity>?){
    artists?.let { (adapter as? ArtistAdapter)?.submitList(it) }
}

