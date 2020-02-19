package com.android.belJomla.authentication


import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.belJomla.LifeCycleCountdownTimer

import com.android.belJomla.R
import com.android.belJomla.utils.Constants.CODE_MAX_LENGTH as CODE_MAX_LENGTH
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.android.belJomla.databinding.FragmentVerificationBinding
import com.android.belJomla.utils.FirebaseUtils


class VerificationFragment : Fragment() {


    lateinit var viewModel: AuthenticationViewModel



    //TODO Move Timer counter to view model
    lateinit var timer : CountDownTimer
    var counter = 15

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentVerificationBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_verification,container,false)
        val viewModel by activityViewModels<AuthenticationViewModel>()




        startResendCounter(binding)


        viewModel.isLoading.observe(this, Observer { isLoading ->

            if (isLoading){
                binding.pbLoading2.visibility = View.VISIBLE
                binding.etVerification.isEnabled = false
            }
            else {
                binding.pbLoading2.visibility = View.GONE
                binding.etVerification.setText("")
                binding.etVerification.isEnabled = true


            }
        })




        binding.etVerification.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                if(editable!!.length == CODE_MAX_LENGTH) {

                    hideKeyboardFrom(context!!,binding.root)
                    viewModel.signInWithPhoneAuthCredential(editable.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        binding.tvResendSms.setOnClickListener {
            FirebaseUtils.verifyPhoneNumber(activity as Activity,viewModel.phoneNumber.value!!,
                viewModel.callbacks.value!!
            )
            startResendCounter(binding)
            viewModel.startLoading()
        }




        return view
    }

    private fun startResendCounter(binding : FragmentVerificationBinding) {
        counter =15
        binding.tvResendSms.visibility = View.GONE

        timer = object : LifeCycleCountdownTimer(lifecycle,15000, 1000)  {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvCounter.text = getString(R.string.did_not_receive_sms_in_42s, counter)
                counter--

            }

            override fun onFinish() {
                binding.tvCounter.text = "You can Resend now"
                binding.tvResendSms.visibility = View.VISIBLE



            }
        }.start()

    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    companion object {
        fun newInstance(): VerificationFragment=
            VerificationFragment()
    }

}
