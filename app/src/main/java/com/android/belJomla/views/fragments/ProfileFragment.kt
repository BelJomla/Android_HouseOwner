package com.android.belJomla.views.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.android.belJomla.R
import com.android.belJomla.databinding.FragmentProfileBinding
import com.android.belJomla.viewmodels.MainViewModel
import com.android.belJomla.views.activities.LoginActivity
import com.android.belJomla.views.activities.SettingsActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val binding : FragmentProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)

        binding.user = viewModel.houseOwnerUser.value

        setOnClickListeners(binding)



        return binding.root
    }

    private fun setOnClickListeners(
        binding: FragmentProfileBinding
    ) {
        val navController = findNavController()
        binding.ivSettings.setOnClickListener {
            val settingsIntent = Intent(context, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
        binding.btnAddress.setOnClickListener {
            Toast.makeText(context, "Address", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.addressesFragment)
        }
        binding.btnHistory.setOnClickListener {
            Toast.makeText(context, "History", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.historyFragment)
        }
        binding.btnBalance.setOnClickListener {
            Toast.makeText(context, "Balance", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.balanceFragment)
        }
        binding.btnPoints.setOnClickListener {
            Toast.makeText(context, getString(R.string.points_soon), Toast.LENGTH_SHORT).show()
        }
    }


}
