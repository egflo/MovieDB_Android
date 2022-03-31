package com.app.moviedb_android.data.model

data class Shipping(
    val city: String,
    val customerId: Int,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val orderId: Int,
    val postcode: String,
    val state: String,
    val street: String,
    val unit: String
)