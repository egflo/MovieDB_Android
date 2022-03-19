package com.app.moviedb_android.ui.cast


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.moviedb_android.R
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.CardAdapter
import com.app.moviedb_android.data.adapter.CastAdapter
import com.app.moviedb_android.data.model.Cast
import com.app.moviedb_android.data.model.Meta
import com.app.moviedb_android.data.model.Page
import com.app.moviedb_android.databinding.FragmentCardBinding
import com.app.moviedb_android.databinding.FragmentCardsBinding
import com.app.moviedb_android.databinding.FragmentCastBinding
import com.app.moviedb_android.ui.card.CardsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CastsFragment : Fragment()  {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentCastBinding? = null
    private val list = ArrayList<Cast>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("CastFragment", "onCreateView")

        _binding = FragmentCastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("CastFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        //recyclerView.adapter = context?.let { CastAdapter(it, list) }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}