package com.app.moviedb_android.data.adapter

import android.R.layout
import android.content.ClipData.Item
import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.app.moviedb_android.R
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.RequestModel.CartRequest
import com.app.moviedb_android.data.ResponseModel.CartResponse
import com.app.moviedb_android.data.model.Cart
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class CartAdapter(private val context: Context, private val items: ArrayList<Cart>): BaseAdapter() {

    //private val inflater: LayoutInflater
    //        = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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

    fun updateQuantity(position: Int, quantity: Int) {
        val currentItem = items[position]
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        // inflate the layout for each list row'
        var view = inflater.inflate(R.layout.cart_item, parent, false)

        // get current item to be displayed
        val currentItem = getItem(position) as Cart

        // get the TextView for item name and item description
        var title: TextView = view.findViewById(R.id.title)
        var price: TextView = view.findViewById(R.id.price)
        var poster: ImageView = view.findViewById(R.id.poster)
        var add: Button = view.findViewById(R.id.plus)
        var subtract: Button = view.findViewById(R.id.minus)
        var remove: Button = view.findViewById(R.id.remove)
        var quantity: TextView = view.findViewById(R.id.quantity)

        remove.setOnClickListener{

            val call = RetrofitClient.getClient().deleteCart(currentItem.id)
            call.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Snackbar.make(view, "Failed to delete item", Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                    if (response.isSuccessful) {
                        val cartResponse = response.body()
                        if (cartResponse != null) {
                            Log.d("CartAdapter", "onResponse: " + cartResponse.toString())
                            items.removeAt(position)
                            notifyDataSetChanged()
                        }
                    }
                }
            })
        }

        add.setOnClickListener {
            val model = CartRequest(currentItem.id, currentItem.userId, currentItem.movieId, currentItem.quantity + 1)
            val callback: Call<CartResponse> = RetrofitClient.getClient().updateCart(model)
            callback.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Snackbar.make(view, "Failed to update cart", Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                    val content = response.body()
                    Log.d("CartUpdate", "onResponse: " + response.isSuccessful)

                    if (response.isSuccessful) {
                        Log.d("CartUpdate", "onResponse: " + content)
                        val new_quantity = content!!.data!!.quantity
                        currentItem.quantity = new_quantity
                        notifyDataSetChanged()

                        Snackbar.make(view, content.message, Snackbar.LENGTH_LONG).show()
                    }

                    if (content != null) {
                        Snackbar.make(view, content.message, Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        Snackbar.make(view, "Failed to update cart", Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

        subtract.setOnClickListener {

            val model = CartRequest(currentItem.id, currentItem.userId, currentItem.movieId, currentItem.quantity - 1)
            val callback: Call<CartResponse> = RetrofitClient.getClient().updateCart(model)
            callback.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Snackbar.make(view, "Failed to update cart", Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                    val content = response.body()
                    Log.d("CartUpdate", "onResponse: " + response.isSuccessful)

                    if (response.isSuccessful) {
                        Log.d("CartUpdate", "onResponse: " + content)
                        val new_quantity = content!!.data!!.quantity
                        currentItem.quantity = new_quantity
                        notifyDataSetChanged()

                        Snackbar.make(view, content.message, Snackbar.LENGTH_LONG).show()
                    }

                    if (content != null) {
                        Snackbar.make(view, content.message, Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        Snackbar.make(view, "Failed to update cart", Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

        //sets the text for item name and item description from the current item object
        //name.text = currentItem.name
        title.text = currentItem.movie!!.title + " (" + currentItem.movie!!.year + ")"
        quantity.text = currentItem.quantity.toString()
        price.text = NumberFormat.getCurrencyInstance(Locale("es", "US")).format(currentItem.movie!!.price)

        Picasso.get()
            .load(currentItem?.movie!!.poster)
            .placeholder(R.drawable.background)
            .error(R.drawable.background)
            //.resize(2048, 1600)
            //.onlyScaleDown() // the image will only be resized if it's bigger than 2048x 1600 pixels.
            .into(poster)

        // returns the view for the current row
        return view
    }

}

