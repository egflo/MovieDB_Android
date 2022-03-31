package com.app.moviedb_android.data.adapter


import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Order
import com.squareup.picasso.Picasso
import inflate
import java.text.NumberFormat
import java.util.*

class OrderAdapter( private val items: ArrayList<Order>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderViewHolder {
        val inflatedView = parent.inflate(R.layout.order_item, false)
        return OrderViewHolder(inflatedView)
    }


    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var ID: Int= 0

        var title: TextView = view.findViewById(R.id.title)
        var quantity: TextView = view.findViewById(R.id.quantity)
        var poster: ImageView = view.findViewById(R.id.poster)
        var price: TextView = view.findViewById(R.id.price)

        fun bind(order: Order) {

            ID = order.id

            title.text = order.movie.title
            quantity.text = "Qty: " + order.quantity.toString()
            price.text = NumberFormat.getCurrencyInstance(Locale("es", "US")).format(order.listPrice)
            Picasso.get()
                .load(order.movie.poster)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .resize(2048, 1600)
                .onlyScaleDown() // the image will only be resized if it's bigger than 2048x 1600 pixels.
                .into(poster)
        }

    }

}