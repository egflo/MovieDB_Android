package com.app.moviedb_android.data.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.app.moviedb_android.MovieActivity
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Meta
import com.app.moviedb_android.data.model.Review
import com.squareup.picasso.Picasso
import inflate

class ReviewAdapter(private val context: Context, private val cards: ArrayList<Review>): RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        val item = cards[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewHolder {
        val inflatedView = parent.inflate(R.layout.fragment_review, false)
        return ReviewHolder(inflatedView)
    }


    inner class ReviewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var review: Review? = null
        private var ID: Int = 0

        var textViewTitle: TextView= view.findViewById(R.id.title)
        var ratingView: RatingBar = view.findViewById(R.id.rating)
        var textViewAuthor: TextView= view.findViewById(R.id.author)
        var textViewContent: TextView= view.findViewById(R.id.content)

        init {

            view.setOnClickListener {
                val context = itemView.context
                //val showIntent = Intent(context, MovieActivity::class.java)
                //showIntent.putExtra("ID", ID)
                //context.startActivity(showIntent)
            }

        }

        fun bind(item: Review) {
            review = item

            textViewTitle.text = item.title
            ratingView.rating = item.rating.toFloat() * 0.5f
            ratingView.numStars = 5

            textViewAuthor.text = item.customer.firstname + " " + item.customer.lastname
            textViewContent.text = item.text

            ID = item.id

        }

    }
}