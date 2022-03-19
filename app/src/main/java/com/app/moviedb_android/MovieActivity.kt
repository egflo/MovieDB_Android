package com.app.moviedb_android

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.data.adapter.CastAdapter
import com.app.moviedb_android.data.adapter.GenreAdapter
import com.app.moviedb_android.data.adapter.ReviewAdapter
import com.app.moviedb_android.data.model.Cast
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.model.Page
import com.app.moviedb_android.data.model.Review
import com.app.moviedb_android.databinding.ActivityMovieBinding
import com.app.moviedb_android.databinding.FragmentCastBinding
import com.app.moviedb_android.ui.review.ReviewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {
    private var context = this@MovieActivity

    lateinit var listGenre: RecyclerView
    lateinit var listCast: ListView

    private var list = ArrayList<Cast>()
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val movieId = intent.getStringExtra("id").toString()
        Log.d("MovieActivity", movieId)


        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        //binding.toolbarLayout.title = "title"
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val ft = supportFragmentManager.beginTransaction()
        val fragment = ReviewsFragment.newInstance(movieId)
        //R.id.placeholder is where we want to load our fragment
       // ft.replace(R.id.container, fragment)
       // ft.commit()

        //supportFragmentManager.beginTransaction()
         //   .replace(R.id.container, fragment)
        //    .commit()

        listCast = binding.layout.listCast!!
        listGenre = binding.layout.listGenre!!

        RetrofitClient.getClient().getMovie(movieId).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val movie = response.body()
                if (movie != null) {
                    binding.title.text = movie.title

                    binding.informationText!!.text = movie.year.toString() + " - " + movie.rated + " - " + movie.runtime + " min"


                    binding.layout.plot!!.text = movie.plot
                    val adapter = CastAdapter(this@MovieActivity, movie.cast)

                    listCast.adapter = adapter
                    binding.toolbarLayout.title = movie.title


                    val adapterGenre = GenreAdapter(this@MovieActivity, movie.genres)
                    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    listGenre.layoutManager = layoutManager
                    listGenre.adapter = adapterGenre


                    Picasso.get()
                        .load(movie.background)
                        .placeholder(R.drawable.background)
                        .error(R.drawable.background)
                        .into(binding.background)

                }

            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("MovieActivity", "onFailure: " + t.message)
                Snackbar.make(binding.root, "Error: " + t.message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        })


        val recycleView = binding.layout.list
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycleView!!.layoutManager = layoutManager

        var callback: Call<Page> = RetrofitClient.getClient().getReviewMovie(movieId)
        callback.enqueue(object : Callback<Page> {
            override fun onFailure(call: Call<Page>, t: Throwable) {
                Log.d("ReviewFragments", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<Page>, response: Response<Page>) {
                if (response.isSuccessful) {
                    val page = response.body()
                    val content = page?.content


                    val convert = Gson().fromJson(content, Array<Review>::class.java)

                    if(convert.isNotEmpty()) {
                        val list = convert.toList() as ArrayList<Review>
                        //Create adapter passing the data
                        val adapter = ReviewAdapter(context, list)
                        //Attach the adapter to the recyclerview to populate items
                        recycleView.adapter = adapter

                    }

                }
                else {
                    Log.d("ReviewFragment", "onResponse: " + response.errorBody())
                }
            }
        })
    }
}

private fun FragmentTransaction.replace(fragmentContainer: FragmentContainerView?, fragment: ReviewsFragment) {

}
