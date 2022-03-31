package com.app.moviedb_android.data.model

data class MovieSimple(
    val id: String,
    val title: String,
    val year: Int,
    val director: String,
    val poster: String,
    val plot: String,
    val rated: String,
    val runtime: String,
    val background: String,
    val updated: Long,
    val price: Double,
)