package com.architectcoders.musictoday.ui.main

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.model.PopularArtists


@BindingAdapter("items")
fun RecyclerView.setItems(artists: List<PopularArtists.Artist>?){
    artists?.let { (adapter as? ArtistAdapter)?.submitList(it) }
}

