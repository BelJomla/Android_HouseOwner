package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController

import com.android.belJomla.R


class BalanceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_balance, container, false)


        val btnBack = view.findViewById<ImageView>(R.id.iv_back)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return view    }


    companion object {

        @JvmStatic
        fun newInstance() =
            BalanceFragment()
    }
}
