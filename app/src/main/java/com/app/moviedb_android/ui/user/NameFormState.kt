package com.app.moviedb_android.ui.user

/**
 * Data validation state of the name form.
 */
data class NameFormState(val firstname: Int? = null,
                         val lastname: Int? = null,
                         val isDataValid: Boolean = false)