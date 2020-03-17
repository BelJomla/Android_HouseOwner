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
import java.util.logging.Logger


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private val countryCode = "+966"
    lateinit var countCodePicker : CountryCodePicker

    private lateinit var  viewModel: AuthenticationViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentLoginBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_login,container,false)



        val viewModel by activityViewModels<AuthenticationViewModel>()
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
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


            var phoneNumber = binding.countryCodePicker.fullNumberWithPlus.replace(" ","")

            LoggerUtils.logMessage(this,"binding.btnLoginNext.setOnClickListener PHone Number is : $phoneNumber")
            LoggerUtils.logMessage(this,"binding.btnLoginNext.setOnClickListener CCP Number is : ${binding.countryCodePicker.fullNumber}")

            if ( !binding.countryCodePicker.isValid) {

                    Toast.makeText(
                    context,
                    "Invalid number in " + binding.countryCodePicker.selectedCountryName + "! Please re-enter it.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {

                if(binding.countryCodePicker.selectedCountryCodeWithPlus == "+966" ) {
                    binding.countryCodePicker.fullNumber = CorrectSaudiPhone(phoneNumber)

                }
                phoneNumber = binding.countryCodePicker.fullNumberWithPlus
                LoggerUtils.logMessage(this,"binding.btnLoginNext.setOnClickListener PHone Number is : $phoneNumber")
                LoggerUtils.logMessage(this,"binding.btnLoginNext.setOnClickListener CCP Number is : ${binding.countryCodePicker.fullNumber}")


                viewModel.setPhoneNumber(phoneNumber)
                FirebaseUtils.verifyPhoneNumber(activity as Activity,viewModel.phoneNumber.value!!,
                    viewModel.callbacks.value!!
                )
                viewModel.startLoading()

               /* Toast.makeText(
                    context,
                    "Correct! Full Number is : " + phoneNumber,
                    Toast.LENGTH_SHORT
                ).show()*/
            }




        }

        binding.countryCodePicker.registerPhoneNumberTextView(binding.etLoginMobile);

        binding.countryCodePicker.setDefaultCountryUsingPhoneCodeAndApply(966);
        binding.countryCodePicker.setCountryPreference("SA,BH,KW,AE,QA");
        binding.countryCodePicker.setOnCountryChangeListener {
                selectedCountry -> Toast.makeText(
            context,
            "Changed to " + selectedCountry.name,
            Toast.LENGTH_SHORT
        ).show()
        }





        return binding.root
    }

    companion object {
        fun newInstance() = LoginFragment()
    }







}

private fun CorrectSaudiPhone(phoneNumber : String): String {

    var correctedPhoneNumber : String = phoneNumber
    if (phoneNumber.startsWith("+966") && phoneNumber[4] =='0' ){

        correctedPhoneNumber = phoneNumber.replaceFirst("0","")
    }

    return correctedPhoneNumber
}
