package com.architectcoders.musictoday.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.ItemArtistBinding
import com.architectcoders.musictoday.ui.common.basicDiffUtil
import com.architectcoders.musictoday.ui.common.inflate

class ArtistAdapter(
    private val listener: (com.architectcoders.musictoday.domain.Artist) -> Unit
) : ListAdapter<com.architectcoders.musictoday.domain.Artist, ArtistAdapter.ViewHolder>(
    basicDiffUtil { old, new -> old.id == new.id }) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemArtistBinding.bind(view)
        fun bind(artist: com.architectcoders.musictoday.domain.Artist){ binding.artist = artist }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(parent.inflate(R.layout.item_artist, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)
        holder.bind(artist)
        holder.itemView.setOnClickListener { listener(artist) }
    }
}



