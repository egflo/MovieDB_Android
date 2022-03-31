package com.app.moviedb_android.ui.bookmark

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
import com.app.moviedb_android.data.model.Bookmark
import com.app.moviedb_android.data.model.Page
import kotlin.contracts.contract


class BookmarksViewModel(var id: String): ViewModel() {

    val bookmarks: MutableLiveData<Page<Bookmark>> by lazy {
        MutableLiveData<Page<Bookmark>>().also {
            loadData()
      }
    }


    private fun loadData() {
        var callback: Call<Page<Bookmark>> = RetrofitClient.getClient().getBookmarks()
        callback.enqueue(object : Callback<Page<Bookmark>> {
            override fun onResponse(call: Call<Page<Bookmark>>, response: Response<Page<Bookmark>>) {
                bookmarks.value = response.body()
            }

            override fun onFailure(call: Call<Page<Bookmark>>, t: Throwable) {
                Log.d("Movie", t.message.toString())
                //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}