package com.architectcoders.musictoday.ui.main

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.PopularArtists


@BindingAdapter("items")
fun RecyclerView.setItems(artists: List<ArtistEntity>?){
    artists?.let { (adapter as? ArtistAdapter)?.submitList(it) }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) = if(visible) visibility = View.VISIBLE
else visibility = View.GONE



