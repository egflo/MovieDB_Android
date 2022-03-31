package com.app.moviedb_android.data.model

data class Sale(
    val customerId: Int,
    val device: String,
    val id: Int,
    val orders: List<Order>,
    val saleDate: Long,
    val salesTax: Double,
    val shipping: Shipping,
    val status: String,
    val stripeId: String,
    val subTotal: Double,
    val total: Double
)