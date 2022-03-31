package com.app.moviedb_android.data.ResponseModel

import com.app.moviedb_android.data.model.Cart
import com.app.moviedb_android.data.model.MovieSimple

data class CartResponse(
    val status: Int,
    val message: String,
    val success: Boolean,
    val data: Cart?,
)