package com.app.moviedb_android.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a Card Fragment"
    }
    val text: LiveData<String> = _text
}