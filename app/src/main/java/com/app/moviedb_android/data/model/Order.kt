package com.app.moviedb_android.data.model

data class Order(
    val id: Int,
    val listPrice: Double,
    val movie: MovieSimple,
    val movieId: String,
    val orderId: Int,
    val quantity: Int
)