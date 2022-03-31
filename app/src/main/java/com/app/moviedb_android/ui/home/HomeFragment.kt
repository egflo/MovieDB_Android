package com.app.moviedb_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.moviedb_android.MainActivity
import com.app.moviedb_android.MovieActivity
import com.app.moviedb_android.R
import com.app.moviedb_android.databinding.FragmentHomeBinding
import com.app.moviedb_android.ui.cards.CardsFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
      //  homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
       // })

        if (savedInstanceState == null) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_rated, CardsFragment().newInstance(true))

                add(R.id.fragment_container_sellers, CardsFragment().newInstance(false))
            }
        }

        //val context = activity
        //val showIntent = Intent(context, MainActivity::class.java)
        //context!!.startActivity(showIntent)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}