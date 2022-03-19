package com.app.moviedb_android.ui.card

import androidx.lifecycle.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModelProvider

class CardViewModelFactory(private val id: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CardViewModel(id) as T
    }

}