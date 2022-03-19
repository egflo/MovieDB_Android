package com.app.moviedb_android.data.model

data class Ratings(
    val imdb: String,
    val metacritic: String,
    val numVotes: Int,
    val rating: Double,
    val rottenTomatoes: String,
    val rottenTomatoesAudience: String,
    val rottenTomatoesAudienceStatus: String,
    val rottenTomatoesStatus: String
)