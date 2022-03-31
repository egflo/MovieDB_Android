package com.app.moviedb_android.data

import com.app.moviedb_android.data.model.Bookmark

class BookmarkResponse (
    val bookmark: Bookmark,
    val success: Boolean,
    val message: String,
)