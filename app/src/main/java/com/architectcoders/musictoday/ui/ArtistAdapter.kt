package com.architectcoders.musictoday.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.ItemArtistBinding
import com.architectcoders.musictoday.model.ArtistLastFmResult
import com.architectcoders.musictoday.model.ArtistLastFmResult.Artists.Artist
import com.architectcoders.musictoday.model.inflate

class ArtistAdapter(
    private val listener: (Artist) -> Unit
) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    var artists = listOf<ArtistLastFmResult.Artists.Artist>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bi = ItemArtistBinding.bind(view)
        fun bind(artist: Artist) = with(bi) {
            artistTitle.text = artist.name

        }
//            movieTitle.text = movie.title
//            movieCover.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
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



