package com.architectcoders.musictoday.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.ItemArtistBinding
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.basicDiffUtil
import com.architectcoders.musictoday.model.inflate
import com.architectcoders.musictoday.model.loadUrl

class ArtistAdapter(
    private val listener: (PopularArtists.Artist) -> Unit
) : ListAdapter<PopularArtists.Artist, ArtistAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    var artists = listOf<PopularArtists.Artist>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bi = ItemArtistBinding.bind(view)
        fun bind(artist: PopularArtists.Artist) = with(bi) {
            artistTitle.text = artist.name
            artistCover.loadUrl(artist.picture_medium)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(parent.inflate(R.layout.item_artist, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = artists[position]
        holder.bind(artist)
        holder.itemView.setOnClickListener { listener(artist) }
    }

    override fun getItemCount(): Int = artists.size
}



