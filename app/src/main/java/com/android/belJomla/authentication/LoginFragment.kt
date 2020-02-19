package com.android.belJomla.authentication


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.belJomla.databinding.FragmentLoginBinding

import com.android.belJomla.R
import com.android.belJomla.utils.FirebaseUtils


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private val countryCode = "+966"
    private lateinit var  viewModel: AuthenticationViewModel

//    lateinit var btnLogin : Button
    //lateinit var navController: NavController
   // lateinit var etMobile  :EditText
   // lateinit var pbLoading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentLoginBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_login,container,false)



        val viewModel by activityViewModels<AuthenticationViewModel>()
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading){
                binding.pbLoading.visibility = View.VISIBLE
                binding.btnLoginNext.isClickable = false
            }
            else {
                binding.pbLoading.visibility = View.GONE
                binding.btnLoginNext.isClickable = true


            }
        })







        binding.btnLoginNext.setOnClickListener {
            val phoneNumber = countryCode + binding.etLoginMobile.text.toString()
            viewModel.setPhoneNumber(phoneNumber)
            FirebaseUtils.verifyPhoneNumber(activity as Activity,viewModel.phoneNumber.value!!,
                viewModel.callbacks.value!!
            )
            viewModel.startLoading()


        }



        return binding.root
    }

    companion object {
        fun getInstance() = LoginFragment()
    }





}
