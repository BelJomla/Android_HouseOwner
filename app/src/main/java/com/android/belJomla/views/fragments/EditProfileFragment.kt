package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.belJomla.R
import com.android.belJomla.databinding.FragmentEditProfileBinding
import com.android.belJomla.viewmodels.SettingsViewModel


class EditProfileFragment : Fragment() {

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentEditProfileBinding  = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile,container,false)

        viewModel.houseOwnerUser.observe(this, Observer { user ->
            binding.houseOwnerUser = user

        })
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading){
                startLoadingView(binding)
            }
            else {
                stopLoadingView(binding)

            }
        })



        binding.btnProfleSave.setOnClickListener{
            val fname = binding.etProfileFname.text.toString()
            val lname = binding.etProfileLname.text.toString()
            val email = binding.etProfileEmail.text.toString()
            when {
                fname.isEmpty() -> Toast.makeText(context,getString(R.string.name_should_not_be_empty),Toast.LENGTH_SHORT).show()
                lname.isEmpty() -> Toast.makeText(context,getString(R.string.name_should_not_be_empty),Toast.LENGTH_SHORT).show()
                email.isNotEmpty() && email.isNotEmail() -> Toast.makeText(context,getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show()
                else -> viewModel.updateProfile(fname,lname,email)
            }

        }

        return binding.root
    }

    private fun stopLoadingView(binding: FragmentEditProfileBinding) {
        binding.pbLoading.visibility = View.GONE
        binding.btnProfleSave.isClickable = true
        binding.etProfileFname.isEnabled = true
        binding.etProfileLname.isEnabled = true
        binding.etProfileEmail.isEnabled = true
    }

    private fun startLoadingView(binding: FragmentEditProfileBinding) {
        binding.pbLoading.visibility = View.VISIBLE
        binding.btnProfleSave.isClickable = false
        binding.etProfileFname.isEnabled = false
        binding.etProfileLname.isEnabled = false
        binding.etProfileEmail.isEnabled = false
    }

    companion object {
        fun getInstance() = EditProfileFragment()
    }


}

private fun String.isNotEmail(): Boolean {
    val pattern = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))"
    return !matches(Regex(pattern))

}
