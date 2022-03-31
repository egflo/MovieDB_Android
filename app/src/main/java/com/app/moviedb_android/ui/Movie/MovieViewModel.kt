package com.app.moviedb_android.ui.Movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.paging.MoviesPagingDataSource
import kotlinx.coroutines.flow.Flow


class MovieViewModel : ViewModel() {

    // Create a LiveData with a String
    val query: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20)) {
        Log.d("MovieViewModel", "query: ${query.value}")
        MoviesPagingDataSource(RetrofitClient.getClient(), query.value!!)
    }.flow
        .cachedIn(viewModelScope)


    override fun onCleared() {
        super.onCleared()
    }

}