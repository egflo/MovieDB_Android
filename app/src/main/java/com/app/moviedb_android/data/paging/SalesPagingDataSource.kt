package com.app.moviedb_android.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.model.Sale
import retrofit2.awaitResponse
import retrofit2.http.Query

private const val STARTING_PAGE_INDEX = 0

open class SalesPagingDataSource(private val service: RetrofitClient) : PagingSource<Int, Sale>() {
    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Sale> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX

            val response = service.getSales(pageNumber).awaitResponse()
            val body = response.body()!!

            Log.d("SalesPagingDataSource", "load: ${body.pageable.pageNumber}")
            val data = body.content

            val nextKey =
                if (data.isEmpty()) null
                else pageNumber + 1//(params.loadSize / NETWORK_PAGE_SIZE)


            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                //prevKey = if(pageNumber == STARTING_PAGE_INDEX) null else pageNumber,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Sale>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}