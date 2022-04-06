package com.app.moviedb_android.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.app.moviedb_android.R
import com.app.moviedb_android.data.adapter.MovieAdapter
import com.app.moviedb_android.databinding.FragmentNotificationsBinding
import com.app.moviedb_android.databinding.FragmentSearchBinding
import com.app.moviedb_android.ui.cards.CardsFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root


        var reyclerView = binding.listView
        val adapter = MovieAdapter()

        reyclerView.apply {
            //layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
            //setHasFixedSize(true)
            var layoutManager = FlexboxLayoutManager(this.context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.justifyContent = JustifyContent.SPACE_EVENLY

            reyclerView.layoutManager = layoutManager
            this.adapter = adapter
        }


        lifecycleScope.launch {
            searchViewModel.movies.asFlow().collectLatest { movies ->
                adapter.submitData(movies)
            }

        }

        if (savedInstanceState == null) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_rated, CardsFragment().newInstance(true))

                add(R.id.fragment_container_sellers, CardsFragment().newInstance(false))
            }
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate menu with items using MenuInflator
        super.onCreateOptionsMenu(menu, inflater)

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

                    return false

                }

                // This method is overridden to filter
                // the adapter according to a search query
                // when the user is typing search
                override fun onQueryTextChange(newText: String): Boolean {

                    Log.d("SearchActivity", "onQueryTextChange: $newText")
                    searchViewModel.setQuery(newText)
                    ///adapter!!.getFilter().filter(newText)
                    return false
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}