package com.app.moviedb_android.ui.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.ReviewAdapter
import com.app.moviedb_android.data.model.Bookmark
import com.app.moviedb_android.data.model.Page
import com.app.moviedb_android.data.model.Review
import com.app.moviedb_android.databinding.FragmentReviewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 */
class ReviewsFragment : Fragment() {

    private var id = ""
    private var _binding: FragmentReviewsBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            Log.d("ReviewsFragment", "onCreate: " + it.getString("id"))
            id = it.getString(ARG_ID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        Log.d("ReviewsFragment", "args: " + arguments)
        val id = arguments?.getString(ARG_ID)

        Log.d("ReviewsFragment", "movieId: $id")

        val recycleView = binding.list
        recycleView.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycleView.layoutManager = layoutManager

        var callback: Call<Page<Review>> = RetrofitClient.getClient().getReviewMovie(id.toString())
        callback.enqueue(object : Callback<Page<Review>> {
            override fun onFailure(call: Call<Page<Review>>, t: Throwable) {
                Log.d("ReviewFragments", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<Page<Review>>, response: Response<Page<Review>>) {
                if (response.isSuccessful) {
                    val page = response.body()
                    val content = page?.content


                   // val convert = Gson().fromJson(content, Array<Review>::class.java)

                    if(content!!.isNotEmpty()) {
                        val list = content as ArrayList<Review>
                        //Create adapter passing the data
                        val adapter = activity?.let { ReviewAdapter(it,list) }
                        //Attach the adapter to the recyclerview to populate items
                        recycleView.adapter = adapter
                        //Set layout manager to position the items

                    }

                }
                else {
                    Log.d("ReviewFragment", "onResponse: " + response.errorBody())
                }
            }
        })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_ID = "id"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(id: String): ReviewsFragment {
            Log.d("ReviewsFragment", "newInstance: $id")

            val fragment = ReviewsFragment()
            val bundle = Bundle()
                //.apply { putString(ARG_ID, id) }
            bundle.putString(ARG_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}