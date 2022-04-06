package com.app.moviedb_android.ui.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.moviedb_android.R
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.CartAdapter
import com.app.moviedb_android.data.model.Cart
import com.app.moviedb_android.data.model.Customer
import com.app.moviedb_android.databinding.FragmentUserBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class UserFragment: Fragment() {
    var thiscontext: Context? = null
    private var _binding: FragmentUserBinding? = null
    private var customer: Customer? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        thiscontext = container!!.context

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val progressBar = binding.progressBar
        val container = binding.userInfoContainer
        val name_container = binding.userNameContainer
        val email_container = binding.userEmailContainer

        name_container.setOnClickListener(View.OnClickListener {

            Log.d("UserFragment", "Clicked")
            //val fragmentManager = activity!!.supportFragmentManager
           // val fragmentTransaction = fragmentManager.beginTransaction()
           // fragmentTransaction.replace(R.id.fragment_container_view, NameFragment())
            //fragmentTransaction.addToBackStack(null)
            //fragmentTransaction.commit()

            var fname = customer!!.firstname
            var lname = customer!!.lastname

            val fragment = NameFragment().newInstance(fname, lname)
            //val container_id = (view as ViewGroup).parent as ViewGroup
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
        })


        email_container.setOnClickListener(View.OnClickListener {
            var email = customer!!.email
            val fragment = EmailFragment().newInstance(email)
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
        })

        var callback: Call<Customer> = RetrofitClient.getClient().getCustomer()

        callback.enqueue(object : Callback<Customer> {
            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Snackbar.make(binding.root, "Error Retrieving User Information", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val content = response.body()

                    customer = content

                    binding.name.text = content!!.firstname + " " + content!!.lastname
                    binding.email.text = content!!.email

                    val addresses = content!!.addresses
                    val primaryAddressId = content!!.primaryAddress
                    val index = addresses.indexOfFirst { it.id == primaryAddressId }
                    if(index != -1) {
                        val address = addresses[index]
                        binding.address.text = address.street + " "  + address.city + " " + address.state + " " + address.postcode
                    }

                    progressBar.visibility = View.GONE
                    container.visibility = View.VISIBLE

                } else {
                    Snackbar.make(binding.root, "Error Retrieving User Information", Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}