package com.android.BelJomla.authentication


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController

import com.android.BelJomla.R
import com.android.BelJomla.utils.FirebaseUtils


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private val countryCode = "+966"
    private lateinit var  viewModel: AuthenticationViewModel

    lateinit var btnLogin : Button
    lateinit var navController: NavController
    lateinit var etMobile  :EditText
    lateinit var pbLoading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        btnLogin = view.findViewById(R.id.btn_login_next)
        etMobile = view.findViewById<EditText>(R.id.et_login_mobile)
        pbLoading = view.findViewById(R.id.pb_loading)


        // Get a reference to the ViewModel scoped to this Fragment
        //val viewModel2 by viewModels<AuthenticationViewModel>()

        // Get a reference to the ViewModel scoped to its Activity
        val viewModel by activityViewModels<AuthenticationViewModel>()
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading){
                pbLoading.visibility = View.VISIBLE
                btnLogin.isClickable = false
            }
            else {
                pbLoading.visibility = View.GONE
                btnLogin.isClickable = true


            }
        })







        btnLogin.setOnClickListener {
            val phoneNumber = countryCode + etMobile.text.toString()
            viewModel.setPhoneNumber(phoneNumber)
            FirebaseUtils.verifyPhoneNumber(activity as Activity,viewModel.phoneNumber.value!!,
                viewModel.callbacks.value!!
            )
            viewModel.startLoading()


        }



        return view
    }





}
