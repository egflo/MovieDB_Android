package com.app.moviedb_android.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import retrofit2.awaitResponse
import retrofit2.http.Query

private const val STARTING_PAGE_INDEX = 0

open class MoviesPagingDataSource(private val service: RetrofitClient, private val query: String) : PagingSource<Int, Movie>() {
    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            Log.d("load", "pageNumber: $pageNumber")
            val response = service.getMoviesByTitle(query, pageNumber).awaitResponse()
            val data = response.body()!!.content

            val nextKey =
                if (data.isEmpty()) null
                else pageNumber + (params.loadSize / NETWORK_PAGE_SIZE)

            LoadResult.Page(
                data = data,
                prevKey = if(pageNumber == STARTING_PAGE_INDEX) null else pageNumber,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}