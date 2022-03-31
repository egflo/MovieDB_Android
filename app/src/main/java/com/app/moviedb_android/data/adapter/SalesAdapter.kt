package com.app.moviedb_android.data.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.MovieActivity
import com.app.moviedb_android.R
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.model.Order
import com.app.moviedb_android.data.model.Sale
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class SalesAdapter :
    PagingDataAdapter<Sale, SalesAdapter.SaleViewHolder>(SaleComparator){

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        val sale = getItem(position)
        holder.bind(sale!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        return SaleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.sale_item, parent, false)
        )
    }

    inner class SaleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var ID: Int = 0

        var date: TextView = view.findViewById(R.id.date)
        var id: TextView = view.findViewById(R.id.id)
        var status: TextView = view.findViewById(R.id.status)
        var total: TextView = view.findViewById(R.id.total)
        var orderList: RecyclerView = view.findViewById(R.id.orderList)

        init {

            view.setOnClickListener {
               // val context = itemView.context
               // val showIntent = Intent(context, OrderActivity::class.java)
               // showIntent.putExtra("id", ID)
               // context.startActivity(showIntent)
            }

        }

        fun bind(sale: Sale) {

            Log.d("SaleAdapter", "bind: ${sale.id}")

            ID = sale.id

            date.text = SimpleDateFormat("dd/MM/yyyy").format(Date(sale.saleDate))
            id.text = "#" + sale.id.toString()
            status.text = sale.status.uppercase()

            val shape = status.background
            var bg = shape.mutate()
            when (sale.status) {
                "pending" -> bg.setTint(itemView.context.getColor(R.color.yellow))
                "processing" -> bg.setTint(itemView.context.getColor(R.color.yellow))
                "shipped" -> bg.setTint(itemView.context.getColor(R.color.green))
                "delivered" -> bg.setTint(itemView.context.getColor(R.color.green))
                "cancelled" -> bg.setTint(itemView.context.getColor(R.color.red))
                "returned" -> bg.setTint(itemView.context.getColor(R.color.red))
            }


            val currency = Currency.getInstance("USD")
            val format = NumberFormat.getCurrencyInstance(Locale.US)
            format.currency = currency
            total.text =  "Total: " + format.format(sale.total)

            val orderAdapter = OrderAdapter(sale.orders as ArrayList<Order>)
            orderList.layoutManager = LinearLayoutManager(itemView.context)
            orderList.adapter = orderAdapter

        }

    }

    object SaleComparator : DiffUtil.ItemCallback<Sale>() {
        override fun areItemsTheSame(oldItem: Sale, newItem: Sale): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sale, newItem: Sale): Boolean {
            return oldItem == newItem
        }
    }

}