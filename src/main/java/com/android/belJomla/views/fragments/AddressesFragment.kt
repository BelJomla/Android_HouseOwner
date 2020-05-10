package com.android.belJomla.views.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.belJomla.R
import com.android.belJomla.adapters.AddressAdapter
import com.android.belJomla.databinding.FragmentAddressesBinding
import com.android.belJomla.decorators.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_addresses.*
import kotlinx.android.synthetic.main.fragment_addresses.appbar
import kotlinx.android.synthetic.main.fragment_shopping.*


class AddressesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentAddressesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_addresses, container, false)
        val navController = findNavController()



        binding.ivBack.setOnClickListener {
            navController.navigateUp()
        }

        binding.rvAddresses.apply {
            adapter = AddressAdapter(requireContext())
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.sixteen_dp).toInt()))
        }

        return binding.root
    }






}
