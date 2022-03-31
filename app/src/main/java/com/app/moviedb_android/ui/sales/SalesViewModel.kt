package com.app.moviedb_android.ui.sales

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.model.Sale
import com.app.moviedb_android.data.paging.MoviesPagingDataSource
import com.app.moviedb_android.data.paging.SalesPagingDataSource
import kotlinx.coroutines.flow.Flow

class SalesViewModel : ViewModel() {


    /*
    * Expose this method to your fragment or activity.
    **/

    val sales: Flow<PagingData<Sale>> = Pager(PagingConfig(pageSize = 20)) {
        SalesPagingDataSource(RetrofitClient.getClient())
    }.flow.cachedIn(viewModelScope)
}