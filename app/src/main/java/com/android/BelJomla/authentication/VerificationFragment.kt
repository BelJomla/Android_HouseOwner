package com.android.BelJomla.authentication


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
import com.android.BelJomla.LifeCycleCountdownTimer

import com.android.BelJomla.R
import com.android.BelJomla.utils.Constants.CODE_MAX_LENGTH as CODE_MAX_LENGTH
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.android.BelJomla.utils.FirebaseUtils


class VerificationFragment : Fragment() {


    lateinit var viewModel: AuthenticationViewModel
    lateinit var tvCounter : TextView
    lateinit var tvResend : TextView
    lateinit var etVerification : EditText
    lateinit var pbLoading : ProgressBar
    lateinit var timer : CountDownTimer
    var counter = 15

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_verification, container, false)
        val viewModel by activityViewModels<AuthenticationViewModel>()


        tvCounter = view.findViewById(R.id.tv_counter)
        tvResend = view.findViewById(R.id.tv_resend_sms)
        etVerification = view.findViewById(R.id.et_verification)
        pbLoading = view.findViewById(R.id.pb_loading2)

        startResendCounter()

        Toast.makeText(context,viewModel.isLoading.value.toString(),Toast.LENGTH_SHORT).show()

        viewModel.isLoading.observe(this, Observer { isLoading ->
            Toast.makeText(context,isLoading.toString(),Toast.LENGTH_SHORT).show()

            if (isLoading){
                pbLoading.visibility = View.VISIBLE
                etVerification.isEnabled = false
            }
            else {
                pbLoading.visibility = View.GONE
                etVerification.setText("")
                etVerification.isEnabled = true


            }
        })




        etVerification.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                if(editable!!.length == CODE_MAX_LENGTH) {

                    hideKeyboardFrom(context!!,view)
                    viewModel.signInWithPhoneAuthCredential(editable.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        tvResend.setOnClickListener {
            FirebaseUtils.verifyPhoneNumber(activity as Activity,viewModel.phoneNumber.value!!,
                viewModel.callbacks.value!!
            )
            startResendCounter()
            viewModel.startLoading()
        }




        return view
    }

    private fun startResendCounter() {
        counter =15
        tvResend.visibility = View.GONE

        timer = object : LifeCycleCountdownTimer(lifecycle,15000, 1000)  {
            override fun onTick(millisUntilFinished: Long) {
                tvCounter.text = getString(R.string.did_not_receive_sms_in_42s, counter)
                counter--

            }

            override fun onFinish() {
                tvCounter.text = "You can Resend now"
                tvResend.visibility = View.VISIBLE



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
