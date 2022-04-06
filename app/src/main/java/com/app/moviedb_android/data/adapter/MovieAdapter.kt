package com.app.moviedb_android.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.MovieActivity
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Movie
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class MovieAdapter :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator){

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var ID: String = ""

        var textView: TextView = view.findViewById(R.id.title)
        var textView2: TextView = view.findViewById(R.id.overview)
        var imageView: ImageView = view.findViewById(R.id.poster)

        init {

            view.setOnClickListener {
                val context = itemView.context
                val showIntent = Intent(context, MovieActivity::class.java)
                showIntent.putExtra("id", ID)
                context.startActivity(showIntent)
            }

        }

        fun bind(movie: Movie) {

            ID = movie.id

            textView.text = movie.title
            textView2.text = movie.year.toString()
            //textView2.text = movie.year.toString() + " - " + movie.rated + " - " + movie.runtime + " min"
            Picasso.get()
                .load(movie.poster)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .resize(2048, 1600)
                .onlyScaleDown() // the image will only be resized if it's bigger than 2048x 1600 pixels.
                .into(imageView)
        }

    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}