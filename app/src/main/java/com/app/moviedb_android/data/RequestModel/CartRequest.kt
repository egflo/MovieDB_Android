package com.app.moviedb_android.data.RequestModel

data class CartRequest (
    val id : Int,
    val userId : Int,
    val movieId : String,
    val qty : Int,
)