package com.app.moviedb_android.data.model

data class Review(
    val created: Long,
    val customer: Customer,
    val customerId: Int,
    val id: Int,
    val movie: MovieSimple,
    val movieId: String,
    val rating: Int,
    val sentiment: String,
    val text: String,
    val title: String
)