package com.app.moviedb_android.data.model

data class Customer(
    val addresses: List<Address>,
    val authorities: List<Authority>,
    val sales: List<Sale>,
    val created: Long,
    val email: String,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val primaryAddress: Int
)