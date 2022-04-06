package com.app.moviedb_android.ui.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.CardAdapter
import com.app.moviedb_android.data.adapter.CartAdapter
import com.app.moviedb_android.data.model.Cart
import com.app.moviedb_android.data.model.Meta
import com.app.moviedb_android.data.model.Page
import com.app.moviedb_android.databinding.FragmentCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartFragment: Fragment() {
    var thiscontext: Context? = null

    private var _binding: FragmentCartBinding? = null
    private var list = ArrayList<Cart>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("CartFragment", "onCreateView")

        thiscontext = container!!.context

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getSubtotal(): String {
        var subtotal = 0.0
        for (i in list) {
            subtotal += i.movie!!.price.toDouble()
        }
        return "Subtotal: " + NumberFormat.getCurrencyInstance(Locale("es", "US")).format(subtotal)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        Log.d("CartFragment", "onViewCreated")

        val progressBar = binding.progressBar
        val layoutView = binding.linearLayout
        val listView = binding.listView
        val adapter = CartAdapter(thiscontext!!,list)
        listView.adapter = adapter


        var callback: Call<ArrayList<Cart>> = RetrofitClient.getClient().getCart()

        callback.enqueue(object : Callback<ArrayList<Cart>> {
            override fun onFailure(call: Call<ArrayList<Cart>>, t: Throwable) {
                Log.d("CardsFragment", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<ArrayList<Cart>>, response: Response<ArrayList<Cart>>) {
                if (response.isSuccessful) {
                    val content = response.body()
                    list = content!!

                    Log.d("CartFragment", "onResponse: " + content)

                    binding.subtotal.text = getSubtotal()


                    //Create adapter passing the data
                    val adapter = CartAdapter(thiscontext!!, list)

                    // Attach the adapter to a ListView
                    binding.listView.adapter = adapter


                    progressBar.visibility = View.GONE
                    layoutView.visibility = View.VISIBLE

                } else {
                    Log.d("CartFragment", "onResponse: " + response.errorBody())
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}