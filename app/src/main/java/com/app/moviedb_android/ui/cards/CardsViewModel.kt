package com.app.moviedb_android.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CardsViewModel : ViewModel() {


    //var retrofit = Retrofit.Builder()
    //    .baseUrl("https://api.example.com")
    //    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    //    .build()

    fun onCardClicked() {
        System.out.println("CardViewModel.onCardClicked()")
    }

    private val _text = MutableLiveData<String>().apply {
        value = "The CardViewModel class is working!"
    }
    val text: LiveData<String> = _text
}