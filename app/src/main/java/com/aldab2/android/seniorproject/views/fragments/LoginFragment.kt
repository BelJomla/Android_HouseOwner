package com.aldab2.android.seniorproject.views.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

import com.aldab2.android.seniorproject.R
import com.aldab2.android.seniorproject.views.FirebaseUtils
import com.aldab2.android.seniorproject.views.activities.MainActivity1


class LoginFragment : Fragment() {

    lateinit var btnLogin : Button
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)




        btnLogin = view.findViewById(R.id.btn_login_next)
        btnLogin.setOnClickListener {
            val mainPageIntent = Intent(context,MainActivity1::class.java)
            mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(mainPageIntent)
            activity!!.finish()

        }



        return view
    }


}
