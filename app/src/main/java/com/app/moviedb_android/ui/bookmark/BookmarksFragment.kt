package com.app.moviedb_android.ui.bookmark

import com.app.moviedb_android.ui.card.CardViewModel
import com.app.moviedb_android.ui.card.CardViewModelFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.R
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.BookmarkAdapter
import com.app.moviedb_android.data.adapter.CardAdapter
import com.app.moviedb_android.data.model.Bookmark
import com.app.moviedb_android.data.model.Meta
import com.app.moviedb_android.data.model.Page
import com.app.moviedb_android.databinding.FragmentCardsBinding
import com.app.moviedb_android.ui.card.CardsViewModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookmarksFragment : Fragment()  {
    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var recyclerView: RecyclerView
    private val list = ArrayList<Meta>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        var title = view.findViewById(R.id.title) as TextView
        title.text = "Your Watchlist"

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = context?.let { CardAdapter(it, list) }

        var callback: Call<Page<Bookmark>> = RetrofitClient.getClient().getBookmarks()
        callback.enqueue(object : Callback<Page<Bookmark>> {
            override fun onFailure(call: Call<Page<Bookmark>>, t: Throwable) {
                Log.d("BookmarkFragment", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<Page<Bookmark>>, response: Response<Page<Bookmark>>) {
                if (response.isSuccessful) {
                    val page = response.body()
                    val content = page?.content
                    //val list = Gson().fromJson(content, Array<Meta>::class.java).toList() as ArrayList<Meta>

                    Log.d("BookmarksFragment", "onResponse: " + content)

                    //Create adapter passing the data
                    val adapter = activity?.let { BookmarkAdapter(it,content as ArrayList<Bookmark>) }
                    //Attach the adapter to the recyclerview to populate items
                    recyclerView.adapter = adapter
                    //Set layout manager to position the items
                }
                else {
                    Log.d("BookmarksFragment", "onResponse: " + response.errorBody())
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}