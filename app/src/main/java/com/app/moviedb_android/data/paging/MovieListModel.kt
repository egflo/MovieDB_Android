package com.app.moviedb_android.data.paging

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieListViewModel(
) : ViewModel() {
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

    //val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
    //    MoviesPagingDataSource(RetrofitClient.getClient(), _query.value?: "A")
    //}.flow.cachedIn(viewModelScope)
}