package com.app.moviedb_android.data.model

data class Cart(
    val createdDate: Long,
    val id: Int,
    val movie: MovieSimple?,
    val movieId: String,
    var quantity: Int,
    val userId: Int
)