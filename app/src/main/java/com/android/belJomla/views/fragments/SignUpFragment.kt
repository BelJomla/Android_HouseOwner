package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

            if (fname.isNotEmpty() && lname.isNotEmpty()) {
                viewModel.createUserInFirestore(fname, lname)
            }
            else {
                Toast.makeText(requireContext(),requireContext().getString(R.string.please_enter_req_fields),Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {isLoading ->
            if (isLoading){
                binding.pbLoading.visibility = View.VISIBLE
                binding.btnSignup.isClickable = false
            }


        })



        return binding.root
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }

}
