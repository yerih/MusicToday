package com.architectcoders.musictoday.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.ItemArtistBinding
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.inflate
import com.architectcoders.musictoday.model.loadUrl

class ArtistAdapter(
    private val listener: (PopularArtists.Data) -> Unit
) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    var artists = listOf<PopularArtists.Data>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bi = ItemArtistBinding.bind(view)
        fun bind(artist: PopularArtists.Data) = with(bi) {
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



