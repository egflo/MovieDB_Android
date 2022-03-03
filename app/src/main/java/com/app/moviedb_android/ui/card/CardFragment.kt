package com.app.moviedb_android.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.moviedb_android.databinding.FragmentCardBinding
import com.app.moviedb_android.databinding.FragmentNotificationsBinding
import com.squareup.picasso.Picasso
import com.app.moviedb_android.R


class CardFragment : Fragment() {

    private lateinit var cardViewModel: CardViewModel
    private var _binding: FragmentCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardViewModel =
            ViewModelProvider(this).get(CardViewModel::class.java)

        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTitle
        val imageView: ImageView = binding.imageView

        Picasso.get()
            .load("https://assets.fanart.tv/fanart/movies/9806/moviebackground/the-incredibles-572618b53f31c.jpg")
            .placeholder(R.drawable.background)
            .error(R.drawable.background)
            .into(imageView)

        cardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}