package com.app.moviedb_android

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.app.moviedb_android.data.BookmarkResponse
import com.app.moviedb_android.data.RequestModel.CartRequest
import com.app.moviedb_android.data.ResponseModel.CartResponse
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
    private var open = false
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

        binding.cart.setOnClickListener { view ->

            val callback: Call<CartResponse> = RetrofitClient.getClient().addCart(movieId)
            callback.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Snackbar.make(view, "Failed to add to cart", Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                    val content = response.body()
                    if (response.isSuccessful) {
                        Snackbar.make(view, content!!.message, Snackbar.LENGTH_LONG).show()
                    }

                    if (content != null) {
                        Snackbar.make(view, content.message, Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        Snackbar.make(view, "Failed to add to cart", Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

        var expand = binding.layout.expand

        var details = binding.layout!!.details
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
        params.setMargins(0, 0, 0, 0)
        details!!.layoutParams = params

        expand!!.setOnClickListener { view ->

            if(details!!.visibility == View.VISIBLE) {
                Log.d("MovieActivity", "expand")

                expand.text = "Show Details"

                details.visibility = View.INVISIBLE
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                params.setMargins(0, 0, 0, 0)
                details.layoutParams = params
            } else {
                Log.d("MovieActivity", "hide")

                expand.text = "Hide Details"

                details.visibility = View.VISIBLE

                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(16, 16, 16, 16)
                details.layoutParams = params
            }
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

        RetrofitClient.getClient().getBookmark(movieId).enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                val bookmarkResponse = response.body()
                if(bookmarkResponse!!.success) {

                    Log.d("MovieActivityBookmark", bookmarkResponse.toString())
                    binding.fab.setImageResource(android.R.drawable.ic_delete)
                }

            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.d("MovieActivity", "onFailure")
                Snackbar.make(binding.root, "Failed to get bookmark", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        })

        RetrofitClient.getClient().getMovie(movieId).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val movie = response.body()
                if (movie != null) {
                    binding.title.text = movie.title

                    binding.informationText!!.text = movie.year.toString() + " - " + movie.rated + " - " + movie.runtime + " min"

                    binding.layout.status!!.text = movie.inventory.status.uppercase()
                    val shape = binding.layout.status!!.background
                    var bg = shape.mutate()
                    when (movie.inventory.status) {
                        "in stock" -> bg.setTint(Color.parseColor("#FF4CAF50"))
                        "out of stock" -> bg.setTint(Color.parseColor("#FF0000"))
                        "limited" -> bg.setTint(Color.parseColor("#FFA500"))
                    }


                    binding.layout.plot!!.text = movie.plot
                    val adapter = CastAdapter(this@MovieActivity, movie.cast)

                    listCast.adapter = adapter
                    binding.toolbarLayout.title = movie.title


                    val adapterGenre = GenreAdapter(this@MovieActivity, movie.genres)
                    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    listGenre.layoutManager = layoutManager
                    listGenre.adapter = adapterGenre

                    val ratings = movie.ratings
                    val imdb = ratings.imdb
                    val rottenTomatoes = ratings.rottenTomatoes
                    val rottenTomatoesAudience = ratings.rottenTomatoesAudience
                    val metaCritic = ratings.metacritic


                    if(imdb != null) {
                        binding.layout.imdbRating!!.text = imdb.toString()
                    }

                    if(!metaCritic.isNullOrEmpty()) {
                        binding.layout.metacriticRating!!.text = metaCritic.toString()
                    }

                    else {
                        binding.layout.metacriticRating!!.text = "N/A"
                    }

                    if(!rottenTomatoes.isNullOrEmpty()) {

                        val status = ratings.rottenTomatoesStatus
                        if(status == "Rotten") {
                            binding.layout.rottenTomatoesIcon!!.setImageResource(R.drawable.rotten_rotten)
                        }
                        if(status == "Certified-Fresh") {
                            binding.layout.rottenTomatoesIcon!!.setImageResource(R.drawable.rotten_cert)

                        }
                        if (status == "Fresh") {
                            binding.layout.rottenTomatoesIcon!!.setImageResource(R.drawable.rotten_fresh)
                        }

                        binding.layout.rottenTomatoesRating!!.text = rottenTomatoes.toString()
                        binding.layout.rottenTomatoesIcon!!.visibility = View.VISIBLE
                        binding.layout.rottenTomatoesRating!!.visibility = View.VISIBLE
                    }

                    if(!rottenTomatoesAudience.isNullOrEmpty()) {

                        val status = ratings.rottenTomatoesAudienceStatus
                        if(status == "Upright") {
                            binding.layout.rottenTomatoesAudienceIcon!!.setImageResource(R.drawable.rotten_upright)
                        }
                        if(status == "Spilled") {
                            binding.layout.rottenTomatoesAudienceIcon!!.setImageResource(R.drawable.rotten_spilled)

                        }

                        binding.layout.rottenTomatoesAudienceScore!!.text = rottenTomatoesAudience.toString()
                        binding.layout.rottenTomatoesAudienceIcon!!.visibility = View.VISIBLE
                        binding.layout.rottenTomatoesAudienceScore!!.visibility = View.VISIBLE
                    }


                    var details = ""

                    if(!movie.writer.isNullOrEmpty()) {
                        details += "Writers(s): " + movie.writer + "\n"
                    }

                    if(!movie.awards.isNullOrEmpty()) {
                        details += "Award(s): " + movie.awards + "\n"
                    }

                    if(!movie.country.isNullOrEmpty()) {
                        details += "Country: " + movie.country + "\n"
                    }

                    if(!movie.language.isNullOrEmpty()) {
                        details += "Language(s): " + movie.language + "\n"
                    }

                    if(!movie.production.isNullOrEmpty()) {
                        details +=  "Production: " + movie.production + "\n"
                    }

                    binding.layout.details!!.text = details

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

        var callback: Call<Page<Review>> = RetrofitClient.getClient().getReviewMovie(movieId)
        callback.enqueue(object : Callback<Page<Review>> {
            override fun onFailure(call: Call<Page<Review>>, t: Throwable) {
                Log.d("ReviewFragments", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<Page<Review>>, response: Response<Page<Review>>) {
                if (response.isSuccessful) {
                    val page = response.body()
                    val content = page?.content


                    //val convert = Gson().fromJson(content, Array<Review>::class.java)

                    if(content?.isNotEmpty() == true) {
                        val list = content.toList() as ArrayList<Review>
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
