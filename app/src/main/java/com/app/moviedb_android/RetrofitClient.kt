package com.app.moviedb_android

import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.model.Page
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitClient {

    @GET("bookmark/all")
    fun getBookmarks(): Call<Page>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: String): Call<Movie>

    @GET("review/movie/{id}")
    fun getReviewMovie(@Path("id") id: String): Call<Page>

    companion object {
        const val BASE_URL = "http://10.81.1.104:8080/"

        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NjEsQUBlbWFpbC5jb20iLCJpc3MiOiJkYXRhZmxpeC5pbyIsImlhdCI6MTY0NzQ4MzM4NCwiZXhwIjoxNjQ4MDg4MTg0fQ.M1T2gwvu_rCYEZBnRzcWnfY9weKJf5HqgSXN4vb7U-u5L5IsnefVJ3QINTX7HUIoxRt893bwsF-HC-OGLZ6u2g")
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