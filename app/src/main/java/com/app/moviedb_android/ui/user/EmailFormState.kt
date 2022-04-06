package com.app.moviedb_android.ui.user

/**
 * Data validation state of the name form.
 */
data class EmailFormState(val email: Int? = null,
                          val confirmEmail: Int? = null,
                          val isDataValid: Boolean = false)