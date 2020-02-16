package com.aldab2.android.seniorproject.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

import com.aldab2.android.seniorproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Carts : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_carts, container, false)
        val btnShop =  view.findViewById<FloatingActionButton>(R.id.btn_shop)

       // if (activity!!.localClassName == MainActivity().localClassName)
        btnShop.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragment_carts_to_fragment_shopping))
        Toast.makeText(view.context,"Clicked ", Toast.LENGTH_SHORT).show()

        return view
    }


}
