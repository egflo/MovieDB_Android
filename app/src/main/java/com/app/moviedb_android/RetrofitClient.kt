package com.app.moviedb_android

import com.app.moviedb_android.data.BookmarkResponse
import com.app.moviedb_android.data.RequestModel.BookmarkRequest
import com.app.moviedb_android.data.RequestModel.CartRequest
import com.app.moviedb_android.data.ResponseModel.CartResponse
import com.app.moviedb_android.data.model.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitClient {

    @GET("movie/title/{title}")
    fun getMoviesByTitle(@Path("title") title: String, @Query("page") currentPage: Int): Call<Page<Movie>>

    @GET("bookmark/all")
    fun getBookmarks(): Call<Page<Bookmark>>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: String): Call<Movie>

    @GET("review/movie/{id}")
    fun getReviewMovie(@Path("id") id: String): Call<Page<Review>>

    @POST("bookmark/")
    fun addBookmark(@Body request: BookmarkRequest): Call<BookmarkResponse>

    @GET("bookmark/{id}")
    fun getBookmark(@Path("id") id: String): Call<BookmarkResponse>

    @GET("order/sellers")
    fun getBestSellers(): Call<Page<Meta>>

    @GET("sale/")
    fun getSales(@Query("page") currentPage: Int): Call<Page<Sale>>

    @GET("rating/rated")
    fun getBestRated(): Call<Page<Meta>>

    @GET("cart/")
    fun getCart(): Call<ArrayList<Cart>>

    @PUT("cart/{id}")
    fun addCart(@Path("id") id: String): Call<CartResponse>

    @POST("cart/")
    fun updateCart(@Body request: CartRequest): Call<CartResponse>

    @DELETE("cart/{id}")
    fun deleteCart(@Path("id") id:Int): Call<CartResponse>

    companion object {
        const val BASE_URL = "http://10.81.1.104:8080/"

        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NjEsQUBlbWFpbC5jb20iLCJpc3MiOiJkYXRhZmxpeC5pbyIsImlhdCI6MTY0ODY4NTQ4OCwiZXhwIjoxNjQ5MjkwMjg4fQ.1_yNBZoK-hyRJUhS3YUMBsVgHEye7HBDGATkfwtKNx5bc3dHXtDkSVFzqqzVY0nTzeeupI-2FxRbk4PeBj2McA")
                .build()
            chain.proceed(newRequest)
        }.build()


        fun getClient(): RetrofitClient {
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitClient::class.java)
        }

    }
}