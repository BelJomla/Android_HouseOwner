package com.android.belJomla.views.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.android.belJomla.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}