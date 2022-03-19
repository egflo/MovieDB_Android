package com.app.moviedb_android.ui.card

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit;
import android.util.Log
import androidx.core.content.contentValuesOf
import kotlin.contracts.contract


class CardViewModel(var id: String): ViewModel() {

    val movie: MutableLiveData<Movie> by lazy {
        MutableLiveData<Movie>().also {
            loadData()
      }
    }

    fun getMovie(): LiveData<Movie> {
        return movie
    }

    private fun loadData() {
        var callback: Call<Movie> = RetrofitClient.getClient().getMovie(id)
        callback.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("Id", id)
                Log.d("Movie", "LOADED")
                movie.value = response.body()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("Movie", t.message.toString())
                //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}