package com.app.moviedb_android.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.app.moviedb_android.data.LoginRepository
import com.app.moviedb_android.data.Result

import com.app.moviedb_android.R
import com.app.moviedb_android.ui.login.LoginFormState
import com.google.android.material.snackbar.Snackbar

class NameViewModel() : ViewModel() {

    private val _form = MutableLiveData<NameFormState>()
    val formState: LiveData<NameFormState> = _form

    fun save(username: String, password: String) {
        // can be launched in a separate asynchronous job
        //Snackbar.make(null, "Login", Snackbar.LENGTH_LONG).show()

    }

    fun dataChanged(username: String, lastname: String) {
        if (!isNameValid(username)) {
            _form.value = NameFormState(firstname = R.string.invalid_first_name)
        } else if (!isNameValid(lastname)) {
            _form.value = NameFormState(lastname = R.string.invalid_last_name)
        } else {
            _form.value = NameFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isNameValid(name: String): Boolean {
        return !name.isBlank()
    }

}