package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.android.belJomla.R
import com.android.belJomla.databinding.FragmentSignUpBinding
import com.android.belJomla.viewmodels.AuthenticationViewModel


class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentSignUpBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_sign_up,container,false)
        val viewModel by activityViewModels<AuthenticationViewModel>()

        binding.btnSignup.setOnClickListener {
            val fname = binding.etFname.text.toString()
            val lname = binding.etLname.text.toString()


            viewModel.createUserInFirestore(fname,lname)
        }

        viewModel.isLoading.observe(this, Observer {isLoading ->
            if (isLoading){
                binding.pbLoading.visibility = View.VISIBLE
                binding.btnSignup.isClickable = false
            }
            else {
                binding.pbLoading.visibility = View.GONE
                binding.btnSignup.isClickable = true
            }

        })


        return binding.root
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }

}
