package com.architectcoders.musictoday.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.databinding.ItemArtistBinding
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.basicDiffUtil
import com.architectcoders.musictoday.model.inflate
import com.architectcoders.musictoday.model.loadUrl

class ArtistAdapter(
    private val listener: (ArtistEntity) -> Unit
) : ListAdapter<ArtistEntity, ArtistAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemArtistBinding.bind(view)
        fun bind(artist: ArtistEntity) = with(binding) {
            artistTitle.text = artist.name
            artistCover.loadUrl(artist.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(parent.inflate(R.layout.item_artist, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)//artists[position]
        holder.bind(artist)
        holder.itemView.setOnClickListener { listener(artist) }
    }
}



