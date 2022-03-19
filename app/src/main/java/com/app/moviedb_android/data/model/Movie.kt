package com.app.moviedb_android.data.model

data class Movie(
    val awards: String,
    val background: String,
    val boxOffice: String,
    val cast: ArrayList<Cast> = ArrayList(),
    val country: String,
    val director: String,
    val genres: ArrayList<Genre> = ArrayList(),
    val id: String,
    val inventory: Inventory,
    val language: String,
    val plot: String,
    val poster: String,
    val price: Double,
    val production: String,
    val rated: String,
    val ratings: Ratings,
    val runtime: String,
    val title: String,
    val writer: String,
    val year: Int
)