package com.app.moviedb_android.data.model

import com.google.gson.annotations.JsonAdapter

data class Meta(
    val id: Long = 0,

    val customerId: Long = 0,

    val movieId: String = "",

    val created: Long = System.currentTimeMillis(),

    val votes: Int = 0,

    val movie: Movie,
)
