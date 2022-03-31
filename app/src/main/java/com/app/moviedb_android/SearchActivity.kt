package com.app.moviedb_android


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.app.moviedb_android.data.adapter.MovieAdapter
import com.app.moviedb_android.data.model.Movie
import com.app.moviedb_android.data.paging.MovieListViewModel
import com.app.moviedb_android.data.paging.MoviesPagingDataSource
import com.app.moviedb_android.databinding.ActivitySearchBinding
import com.app.moviedb_android.ui.notifications.NotificationsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private var movieListViewModel: MovieListViewModel = MovieListViewModel()



    var listView: ListView? = null
    var list: ArrayList<String> = ArrayList()
    var adapter: ArrayAdapter<String>? = null

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var reyclerView = binding.listView
        val adapter = MovieAdapter()

        reyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
            this.adapter = adapter
        }


        lifecycleScope.launch {
            movieListViewModel.movies.asFlow().collectLatest { movies ->
                adapter.submitData(movies)
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu with items using MenuInflator
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        // Initialise menu item search bar
        // with id and take its object
        val searchViewItem: MenuItem = menu.findItem(R.id.search_bar)
        val searchView: SearchView = searchViewItem.actionView as SearchView

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                // Override onQueryTextSubmit method
                // which is call
                // when submitquery is searched
                override fun onQueryTextSubmit(query: String): Boolean {
                    // If the list contains the search query
                    // than filter the adapter
                    // using the filter method
                    // with the query as its argument
                    if (list.contains(query)) {
                       // adapter!!.getFilter().filter(query)
                    } else {
                        // Search query not found in List View
                        Toast
                            .makeText(
                                this@SearchActivity,
                                "Not found",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                    return false
                }

                // This method is overridden to filter
                // the adapter according to a search query
                // when the user is typing search
                override fun onQueryTextChange(newText: String): Boolean {

                    Log.d("SearchActivity", "onQueryTextChange: $newText")
                    movieListViewModel.setQuery(newText)
                    ///adapter!!.getFilter().filter(newText)
                    return false
                }
            })
        return super.onCreateOptionsMenu(menu)
    }

}

private fun SearchView.setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}