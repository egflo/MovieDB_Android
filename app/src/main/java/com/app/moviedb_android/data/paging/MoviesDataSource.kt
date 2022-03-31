package com.app.moviedb_android.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.model.Page
import java.util.concurrent.Flow

const val NETWORK_PAGE_SIZE = 25

//internal class MoviesDataSourceImpl(
    //private val service: RetrofitClient) : MoviesDataSource {

    /**
     *
     *     override fun getMovies(): Flow<PagingData<Movie>> {
    return Pager(
    config = PagingConfig(
    pageSize = NETWORK_PAGE_SIZE,
    enablePlaceholders = false,
    initialLoadSize = NETWORK_PAGE_SIZE * 2,
    prefetchDistance = NETWORK_PAGE_SIZE,
    maxSize = 200
    ),
    pagingSourceFactory = { MoviesPagingDataSource(service) }
    ).flow
    }
     *
     *
     *
     */

//}


