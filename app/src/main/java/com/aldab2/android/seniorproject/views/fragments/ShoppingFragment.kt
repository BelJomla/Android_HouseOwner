package com.aldab2.android.seniorproject.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.aldab2.android.seniorproject.R
import com.aldab2.android.seniorproject.views.FirebaseUtils


class ShoppingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)
        FirebaseUtils.isLoggedIn = true
        return view
    }


}
