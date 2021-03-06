package com.app.moviedb_android.ui.sales

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.app.moviedb_android.data.adapter.MovieAdapter
import com.app.moviedb_android.data.adapter.SalesAdapter
import com.app.moviedb_android.databinding.FragmentSalesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SalesFragment : Fragment() {

    private lateinit var salesViewModel: SalesViewModel
    private var _binding: FragmentSalesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        salesViewModel =
            ViewModelProvider(this).get(SalesViewModel::class.java)

        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("SalesFragment", "onCreateView")

        var reyclerView = binding.listView
        val adapter = SalesAdapter()

        //val progressBar = binding.progressBar

        reyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
            setHasFixedSize(true)
            this.adapter = adapter
            //progressBar.visibility = View.INVISIBLE
            //reyclerView.visibility = View.VISIBLE
        }

        lifecycleScope.launch {
            salesViewModel.sales.collectLatest { sale ->
                Log.d("SalesFragment", "collectLatest")
                adapter.submitData(sale)
            }

            adapter.loadStateFlow.collectLatest { loadState ->
                Log.d("SalesFragment", (loadState.refresh is LoadState.Loading).toString())

                //binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            }


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}