package com.app.moviedb_android.data.adapter

import android.R.layout
import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.MovieActivity
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Cast
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject


class CastAdapter(private val context: Context, private val cards: ArrayList<Cast>): BaseAdapter() {

    //private val inflater: LayoutInflater
    //        = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val inflater: LayoutInflater
        = LayoutInflater.from(context)

    override fun getItem(position: Int): Any {
        return cards[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return cards.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        // inflate the layout for each list row'
        var view = inflater.inflate(R.layout.cast_item, parent, false)

        // get current item to be displayed
        val currentItem = getItem(position) as Cast

        // get the TextView for item name and item description
        var name: TextView = view.findViewById(R.id.name)
        var imageView: ImageView = view.findViewById(R.id.cast_image)
        var character: TextView = view.findViewById(R.id.character)

        //sets the text for item name and item description from the current item object
        name.text = currentItem.name

        if(currentItem.characters.isNotEmpty()) {
            val json = JSONArray(currentItem.characters)
            character.text = json.get(0) as CharSequence?
        }
        else {
            character.text = ""
        }
        Picasso.get()
            .load(currentItem?.photo)
            .placeholder(com.app.moviedb_android.R.drawable.person)
            .error(com.app.moviedb_android.R.drawable.person)
            .resize(2048, 1600)
            .onlyScaleDown() // the image will only be resized if it's bigger than 2048x 1600 pixels.
            .into(imageView)

        // returns the view for the current row
        return view
    }

}

