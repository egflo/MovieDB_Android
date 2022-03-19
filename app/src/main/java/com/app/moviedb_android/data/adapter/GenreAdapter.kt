package com.app.moviedb_android.data.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Genre
import com.google.android.material.button.MaterialButton
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import inflate


class GenreAdapter(private val context:Context, private val cards: ArrayList<Genre>): RecyclerView.Adapter<GenreAdapter.CardHolder>() {

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val item = cards[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.CardHolder {
        val inflatedView = parent.inflate(R.layout.fragment_genre, false)
        return CardHolder(inflatedView)
    }

    inner class CardHolder(view: View): RecyclerView.ViewHolder(view) {
        private var meta: Genre? = null
        private var ID: Int = 0

        var button: MaterialButton= view.findViewById(R.id.genre_button)

        init {

            view.setOnClickListener {
                val context = itemView.context
                Log.d("GenreAdapter", "Genre ID: ${meta?.id}")
                //al showIntent = Intent(context, MovieActivity::class.java)
                //showIntent.putExtra("id", ID)
                //context.startActivity(showIntent)
            }

        }

        fun bind(item: Genre) {
            meta = item
            ID = item.id

            //sets the text for item name and item description from the current item object
            button.text = item.name
        }

    }
}


/**
class GenreAdapter(private val context: Context, private val items: ArrayList<Genre>): BaseAdapter() {

private val inflater: LayoutInflater
= LayoutInflater.from(context)

override fun getItem(position: Int): Any {
return items[position]
}

override fun getItemId(position: Int): Long {
return position.toLong()
}

override fun getCount(): Int {
return items.size
}

override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

// inflate the layout for each list row'
var view = inflater.inflate(R.layout.fragment_genre, parent, false)

// get current item to be displayed
val currentItem = getItem(position) as Genre

// get the TextView for item name and item description
var button: MaterialButton = view.findViewById(R.id.genre_button)

//sets the text for item name and item description from the current item object
button.text = currentItem.name

// returns the view for the current row
return view
}

}
 */


