package com.app.moviedb_android.ui.cards

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.R
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.CardAdapter
import com.app.moviedb_android.data.model.Bookmark
import com.app.moviedb_android.data.model.Meta
import com.app.moviedb_android.data.model.Page
import com.app.moviedb_android.databinding.FragmentCardBinding
import com.app.moviedb_android.databinding.FragmentCardsBinding
import com.app.moviedb_android.ui.card.CardsViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardsFragment : Fragment()  {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var cardViewModel: CardsViewModel


    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentCardsBinding? = null
    private val list = ArrayList<Meta>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun newInstance(type: Boolean): CardsFragment {

        val args = Bundle()
        args.putBoolean("type", type)
        val fragment = CardsFragment()
        fragment.arguments = args
        return fragment

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("CardsFragment", "onCreateView")

        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("CardsFragment", "onViewCreated")

        super.onViewCreated(view, savedInstanceState);


        binding.title.text = (if (arguments?.getBoolean("type")!!) "Best Sellers" else "Popular")

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = context?.let { CardAdapter(it, list) }


       var callback: Call<Page<Meta>> = (if (arguments?.getBoolean("type")!!) RetrofitClient.getClient().getBestSellers() else RetrofitClient.getClient().getBestRated())

        callback.enqueue(object : Callback<Page<Meta>> {
            override fun onFailure(call: Call<Page<Meta>>, t: Throwable) {
                Log.d("CardsFragment", "onFailure: " + t.message)
            }

        override fun onResponse(call: Call<Page<Meta>>, response: Response<Page<Meta>>) {
        if (response.isSuccessful) {
            val page = response.body()
            val content = page?.content
            //val list = Gson().fromJson(content, Array<Meta>::class.java).toList() as ArrayList<Meta>

            Log.d("CardsFragment", "onResponse: " + content)

            //Create adapter passing the data
            val adapter = activity?.let { CardAdapter(it,content as ArrayList<Meta> /* = java.util.ArrayList<com.app.moviedb_android.data.model.Meta> */) }
            //Attach the adapter to the recyclerview to populate items
            recyclerView.adapter = adapter
            //Set layout manager to position the items
            }
            else {
                Log.d("CardsFragment", "onResponse: " + response.errorBody())
            }
        }
    })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}