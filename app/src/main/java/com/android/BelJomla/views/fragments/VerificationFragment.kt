package com.android.BelJomla.views.fragments


import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.android.BelJomla.R
import com.android.BelJomla.views.models.NavigationMessage
import org.greenrobot.eventbus.EventBus


class VerificationFragment : Fragment() {

    private val VERIFICATION_MESSAGE_LENGTH = 4
    lateinit var tvCounter : TextView
    lateinit var tvResend : TextView
    lateinit var etVerification : EditText
    lateinit var timer : CountDownTimer
    var counter = 60

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_verification, container, false)
        tvCounter = view.findViewById(R.id.tv_counter)
        tvResend = view.findViewById(R.id.tv_resend_sms)
        etVerification = view.findViewById(R.id.et_verification)

        startResendCounter()
        tvResend.setOnClickListener {
            // TODO Implement the resend code
            startResendCounter()
        }


        etVerification.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                if(editable!!.length == VERIFICATION_MESSAGE_LENGTH) {
                    if (editable.toString() == "1234"){
                        Toast.makeText(context,getString(R.string.wrong_verif_code),Toast.LENGTH_SHORT).show()
                    }
                    else {
                        timer.cancel()

                        val verifyEvent = NavigationMessage()
                        verifyEvent.VerifactionComplete()
                        EventBus.getDefault().post(verifyEvent)

                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })




        return view
    }

    private fun startResendCounter() {
        counter =60
        tvResend.visibility = View.GONE

        timer = object : CountDownTimer(60000, 1000) {
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


}
