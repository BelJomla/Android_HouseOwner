package com.android.belJomla.views.fragments

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceFragmentCompat
import com.android.belJomla.R
import com.android.belJomla.viewmodels.SettingsViewModel

class SettingsFragment : PreferenceFragmentCompat() {


    private val viewModel by activityViewModels<SettingsViewModel>()
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        viewModel.user.observe(this, Observer {

        })



    }
}