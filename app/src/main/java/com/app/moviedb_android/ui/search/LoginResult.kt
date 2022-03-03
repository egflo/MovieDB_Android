package com.app.moviedb_android.ui.search

import com.app.moviedb_android.ui.search.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUserView? = null,
        val error: Int? = null
)