package com.app.moviedb_android.data.ResponseModel

import com.app.moviedb_android.data.model.Bookmark

data class BookmarkResponse (
    var bookmark: Bookmark? = null,
    var success: Boolean? = false,
    var message: String? = null

)