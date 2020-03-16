package com.android.belJomla.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.belJomla.R
import com.android.belJomla.viewmodels.SettingsViewModel
import com.android.belJomla.views.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {


    private val viewModel by activityViewModels<SettingsViewModel>()
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        viewModel.houseOwnerUser.observe(this, Observer {

        })

        val btnSignOut = findPreference<Preference>(getString(R.string.settings_signout))

        btnSignOut!!.setOnPreferenceClickListener {
            FirebaseAuth.getInstance().signOut()
            val signOutIntent = Intent(context,
                LoginActivity::class.java)
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(signOutIntent)
            requireActivity().finish()
            true
        }



    }
}