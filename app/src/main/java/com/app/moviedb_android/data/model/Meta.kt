package com.app.moviedb_android.data.model

import com.google.gson.annotations.JsonAdapter

data class Meta(
    val id: Long = 0,

    val customerId: Long = 0,

    val created: Long = 0,

    val movieId: String = "",

    val votes: Int = 0,

    val sales: Int = 0,

    val movie: MovieSimple,
)
