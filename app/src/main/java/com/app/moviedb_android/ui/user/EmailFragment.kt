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
import com.app.moviedb_android.RetrofitClient
import com.app.moviedb_android.data.model.Customer
import com.app.moviedb_android.databinding.FragmentEmailBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EmailFragment: Fragment() {
    var thiscontext: Context? = null
    private var _binding: FragmentEmailBinding? = null
    private lateinit var viewModel: EmailViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    fun newInstance(email: String,): EmailFragment {
        val args = Bundle()
        args.putString("email", email)
        val fragment = EmailFragment()
        fragment.setArguments(args)
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        thiscontext = container!!.context

        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val intial = arguments?.getString("email")

        val email = binding.email
        val confirmEmail = binding.confirmEmail
        val save = binding.save
        val loading = binding.loading

        //email.text = Editable.Factory.getInstance().newEditable(email)
        //lastname.text = Editable.Factory.getInstance().newEditable(lname)


        viewModel = ViewModelProvider(this).get(EmailViewModel::class.java)

        viewModel.formState.observe(viewLifecycleOwner) {
            val state = it ?: return@observe
            save.isEnabled = state.isDataValid
            state.email?.let {
                email.error = getString(it)
            }
            state.confirmEmail?.let {
                confirmEmail.error = getString(it)
            }
        }


        email.afterTextChanged {
            viewModel.dataChanged(
                email.text.toString(),
                confirmEmail.text.toString()
            )
        }

        confirmEmail.afterTextChanged {
            viewModel.dataChanged(
                email.text.toString(),
                confirmEmail.text.toString()
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
