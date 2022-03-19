package com.app.moviedb_android.data.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.MovieActivity
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Meta
import com.app.moviedb_android.ui.card.CardFragment
import com.squareup.picasso.Picasso
import inflate


class CardAdapter(private val context:Context, private val cards: ArrayList<Meta>): RecyclerView.Adapter<CardAdapter.CardHolder>() {

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val item = cards[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CardHolder {
        //val context = parent.context
        //val inflater = LayoutInflater.from(context)
        //val inflatedView = inflater.inflate(R.layout.fragment_card, parent, false)
        //return CardHolder(inflatedView)
        val inflatedView = parent.inflate(R.layout.fragment_card, false)
        return CardHolder(inflatedView)
    }


     inner class CardHolder(view: View): RecyclerView.ViewHolder(view) {
        private var meta: Meta? = null
        private var ID: String = ""

        var textView: TextView= view.findViewById(R.id.text)
        var imageView: ImageView = view.findViewById(R.id.imageView)
        var textView2: TextView = view.findViewById(R.id.text2)

        init {

            view.setOnClickListener {
                val context = itemView.context
                val showIntent = Intent(context, MovieActivity::class.java)
                showIntent.putExtra("id", ID)
                context.startActivity(showIntent)
            }

        }

        fun bind(item: Meta) {
            meta = item
            val movie = item.movie
            ID = movie.id

            textView.setTextColor(Color.WHITE)
            textView.setShadowLayer(5f, 0f, 0f, Color.BLACK)

            textView2.setTextColor(Color.WHITE)
            textView2.setShadowLayer(5f, 0f, 0f, Color.BLACK)

            textView.text = movie.title
            textView2.text = movie.year.toString()
            Picasso.get()
                .load(movie.background)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .into(imageView)

            //val cardFragment = CardFragment.newInstance(item.movieId)
            //cardFragment.show(context.supportFragmentManager, "card")

        }

    }
}

