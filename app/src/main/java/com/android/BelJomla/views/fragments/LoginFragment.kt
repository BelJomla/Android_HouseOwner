package com.android.BelJomla.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController

import com.android.BelJomla.R
import com.android.BelJomla.views.models.AuthenticationMessage
import org.greenrobot.eventbus.EventBus


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private val countryCode = "+966"

    lateinit var btnLogin : Button
    lateinit var navController: NavController
    lateinit var etMobile  :EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)






        btnLogin = view.findViewById(R.id.btn_login_next)
        etMobile = view.findViewById<EditText>(R.id.et_login_mobile)


        btnLogin.setOnClickListener {
            val phoneNumber = countryCode + etMobile.text.toString()
            Log.i("XEXEXEXE","Phone is $phoneNumber")
          //  FirebaseUtils.verifyPhoneNumber(activity as Activity,phoneNumber,callbacks)

            val event  = AuthenticationMessage()
            event.eventLoginNext(phoneNumber)
            EventBus.getDefault().post(event)

        }



        return view
    }





}
