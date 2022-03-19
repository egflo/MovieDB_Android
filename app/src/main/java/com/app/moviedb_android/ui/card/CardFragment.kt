package com.app.moviedb_android.ui.card

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
import com.app.moviedb_android.R
import com.app.moviedb_android.databinding.FragmentCardBinding
import com.squareup.picasso.Picasso


class CardFragment : Fragment()  {

    private lateinit var cardViewModel: CardViewModel
    private var _binding: FragmentCardBinding? = null
    private var id: String = "tt1375666"


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: String): CardFragment {
            val fragment = CardFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("CardFragment", "onCreateView")

        cardViewModel = ViewModelProvider(this, CardViewModelFactory("tt0468569")).get(CardViewModel::class.java)
            //ViewModelProviders

        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //root.setOnClickListener {
       //     cardViewModel.onCardClicked()
       // }

        val textView: TextView = binding.text
        val textView2: TextView = binding.text2
        val imageView: ImageView = binding.imageView

        textView.setTextColor(Color.WHITE)
        textView.setShadowLayer(5f, 0f, 0f, Color.BLACK)

        textView2.setTextColor(Color.WHITE)
        textView2.setShadowLayer(5f, 0f, 0f, Color.BLACK)


        cardViewModel.movie.observe(viewLifecycleOwner, Observer {
            textView.text = it.title
            textView2.text = it.year.toString()
            Picasso.get()
                .load(it.background)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .into(imageView)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}