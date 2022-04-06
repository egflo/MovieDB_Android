package com.app.moviedb_android.ui.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.moviedb_android.MainActivity
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.adapter.CartAdapter
import com.app.moviedb_android.data.model.Cart
import com.app.moviedb_android.data.model.Customer
import com.app.moviedb_android.databinding.FragmentNameBinding
import com.app.moviedb_android.databinding.FragmentUserBinding
import com.app.moviedb_android.ui.login.LoginViewModel
import com.app.moviedb_android.ui.login.LoginViewModelFactory
import com.app.moviedb_android.ui.login.afterTextChanged
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class NameFragment: Fragment() {
    var thiscontext: Context? = null
    private var _binding: FragmentNameBinding? = null
    private lateinit var viewModel: NameViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    fun newInstance(firstname: String, lastname:String): NameFragment {
        val args = Bundle()
        args.putString("firstname", firstname)
        args.putString("lastname", lastname)
        val fragment = NameFragment()
        fragment.setArguments(args)
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        thiscontext = container!!.context

        _binding = FragmentNameBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val fname = arguments?.getString("firstname")
        val lname = arguments?.getString("lastname")

        val firstname = binding.firstname
        val lastname = binding.lastname
        val save = binding.save
        val loading = binding.loading

        firstname.text = Editable.Factory.getInstance().newEditable(fname)
        lastname.text = Editable.Factory.getInstance().newEditable(lname)


        viewModel = ViewModelProvider(this).get(NameViewModel::class.java)

        viewModel.formState.observe(viewLifecycleOwner) {
            val state = it ?: return@observe
            save.isEnabled = state.isDataValid
            state.firstname?.let {
                firstname.error = getString(it)
            }
            state.lastname?.let {
                lastname.error = getString(it)
            }
        }


        firstname.afterTextChanged {
            viewModel.dataChanged(
                firstname.text.toString(),
                lastname.text.toString()
            )
        }

        lastname.afterTextChanged {
            viewModel.dataChanged(
                firstname.text.toString(),
                lastname.text.toString()
            )
        }

        var callback: Call<Customer> = RetrofitClient.getClient().getCustomer()

        callback.enqueue(object : Callback<Customer> {
            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Snackbar.make(binding.root, "Error Retrieving User Information", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val content = response.body()

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

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}