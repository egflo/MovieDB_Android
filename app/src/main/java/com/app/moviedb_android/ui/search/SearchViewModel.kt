package com.app.moviedb_android.ui.search

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.paging.MoviesPagingDataSource

class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Search Fragment"
    }
    val text: LiveData<String> = _text

    val _query = MutableLiveData<String>()

    /*
    * Expose this method to your fragment or activity.
    **/

    fun setQuery(query: String) {
        _query.value = query
    }

    val movies = _query.switchMap { query ->

        Log.d("MovieListViewModel", "query: $query")

        val config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20,
            prefetchDistance = 20
        )
        Pager(config) {
            MoviesPagingDataSource(RetrofitClient.getClient(), _query.value?: "A")
        }.liveData.cachedIn(viewModelScope)
    }
}