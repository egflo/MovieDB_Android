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

class EmailViewModel() : ViewModel() {

    private val _form = MutableLiveData<EmailFormState>()
    val formState: LiveData<EmailFormState> = _form

    fun save(username: String, password: String) {
        // can be launched in a separate asynchronous job
        //Snackbar.make(null, "Login", Snackbar.LENGTH_LONG).show()

    }

    fun dataChanged(email: String, confirm: String) {
        if (!isEmailValid(email)) {
            _form.value = EmailFormState(email = R.string.invalid_username)
        } else if (!isEmailConfirmValid(email, confirm)) {
            _form.value = EmailFormState(confirmEmail = R.string.invalid_username)
        } else {
            _form.value = EmailFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isEmailValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isEmailConfirmValid(email:String, Confirm:String): Boolean {

        return email == Confirm

    }

}