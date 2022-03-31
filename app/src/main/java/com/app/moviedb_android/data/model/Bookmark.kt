package com.app.moviedb_android.data.model

data class Bookmark(
    val id: Long,

    val customerId: Long,

    val movieId: String,

    val created: Long,

    val movie: MovieSimple
)