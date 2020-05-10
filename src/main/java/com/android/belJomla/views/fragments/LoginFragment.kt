package com.android.belJomla.views.fragments


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.belJomla.databinding.FragmentLoginBinding
import com.android.belJomla.R
import com.android.belJomla.utils.FirebaseUtils
import com.android.belJomla.utils.LoggerUtils
import com.android.belJomla.viewmodels.AuthenticationViewModel
import com.rilixtech.widget.countrycodepicker.CountryCodePicker


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private val countryCode = "+966"
    lateinit var countCodePicker : CountryCodePicker



    val viewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentLoginBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_login,container,false)



        initObservers(binding)







        binding.btnLoginNext.setOnClickListener {
            handleEnteredPhoneNumber(binding)
        }

        setupCountryCodePicker(binding)




        return binding.root
    }

    private fun initObservers(binding: FragmentLoginBinding) {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.pbLoading.visibility = View.VISIBLE
                binding.btnLoginNext.isClickable = false
            } else {
                binding.pbLoading.visibility = View.GONE
                binding.btnLoginNext.isClickable = true


            }
        })
        viewModel.eventVerificationFailedTooManyRequests.observe(viewLifecycleOwner, Observer { hasFailed ->
            if (hasFailed) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.too_many_requests), Toast.LENGTH_LONG).show()
                viewModel.onVerificationComplete()
            }
        })

    }

    private fun setupCountryCodePicker(binding: FragmentLoginBinding) {
        binding.countryCodePicker.registerPhoneNumberTextView(binding.etLoginMobile)

        binding.countryCodePicker.setDefaultCountryUsingPhoneCodeAndApply(966)
        binding.countryCodePicker.setCountryPreference("SA,BH,KW,AE,QA")
    }

    private fun handleEnteredPhoneNumber(
        binding: FragmentLoginBinding

    ) {
        var phoneNumber = binding.countryCodePicker.fullNumberWithPlus.replace(" ", "")

        LoggerUtils.logMessage(
            this,
            "binding.btnLoginNext.setOnClickListener PHone Number is : $phoneNumber"
        )
        LoggerUtils.logMessage(
            this,
            "binding.btnLoginNext.setOnClickListener CCP Number is : ${binding.countryCodePicker.fullNumber}"
        )

        if (!binding.countryCodePicker.isValid) {
            Toast.makeText(
                context,
                getString(R.string.invalid_num_in_x,binding.countryCodePicker.selectedCountryName),
                Toast.LENGTH_SHORT
            ).show()
        } else {

            if (binding.countryCodePicker.selectedCountryCodeWithPlus == "+966") {
                binding.countryCodePicker.fullNumber = correctSaudiPhone(phoneNumber)

            }
            phoneNumber = binding.countryCodePicker.fullNumberWithPlus
            LoggerUtils.logMessage(
                this,
                "binding.btnLoginNext.setOnClickListener PHone Number is : $phoneNumber"
            )
            LoggerUtils.logMessage(
                this,
                "binding.btnLoginNext.setOnClickListener CCP Number is : ${binding.countryCodePicker.fullNumber}"
            )
            viewModel.setPhoneNumber(phoneNumber)
            FirebaseUtils.verifyPhoneNumber(
                activity as Activity, viewModel.phoneNumber.value!!,
                viewModel.callbacks.value!!
            )
            viewModel.startLoading()


        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }



    private fun correctSaudiPhone(phoneNumber : String): String {

        var correctedPhoneNumber : String = phoneNumber
        if (phoneNumber.startsWith("+966") && phoneNumber[4] =='0' ){

            correctedPhoneNumber = phoneNumber.replaceFirst("0","")
        }

        return correctedPhoneNumber
    }



}


